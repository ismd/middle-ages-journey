package game.world;

import com.google.gson.Gson;
import game.Online;
import game.dao.DaoFactory;
import game.dao.MobDao;
import game.layout.Cell;
import game.layout.Layout;
import game.mob.Mob;
import game.mob.MobAvailableCell;
import game.mob.MobInfo;
import game.npc.Npc;
import game.server.response.Response;
import game.world.exceptions.BadCoordinatesException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ismd
 */
public class World {

    private static Logger log = LoggerFactory.getLogger(World.class);
    public static Map<Integer, Layout> layouts;

    private static Map<Integer, Mob> mobs = new HashMap<>();
    private static Map<Integer, MobInfo> mobInfos = new HashMap<>();

    private static Map<Integer, Npc> npcs = new HashMap<>();

    public World(String dir) throws FileNotFoundException {
        log.info("Initializing world");
        initializeLayouts(dir);
        initializeMobs();
        initializeNpcs();
    }

    private void initializeLayouts(String dir) throws FileNotFoundException {
        log.info("Initializing layouts " + dir);

        Gson gson = new Gson();
        FileReader reader;

        layouts = DaoFactory.getInstance().layoutDao.getAll();
        for (Entry<Integer, Layout> layout : layouts.entrySet()) {
            log.info("Parsing layout ({}) {}",
                    layout.getValue().getId(),
                    layout.getValue().getTitle()
            );

            reader = new FileReader(dir + "/" + layout.getValue().getId() + ".txt");
            layout.getValue().setCells(gson.fromJson(reader, int[][][].class));
        }
    }

    private void initializeMobs() {
        log.info("Initializing mobs");

        MobDao mobDao = DaoFactory.getInstance().mobDao;
        Random random = new Random(System.currentTimeMillis());

        for (MobInfo mobInfo : mobDao.getAllAvailable()) {
            addMobInfo(mobInfo);

            int maxInWorld = mobInfo.getMaxInWorld();
            List<MobAvailableCell> availableCells = mobDao.getAvailableCells(mobInfo);
            mobInfo.setAvailableCells(availableCells);

            if (0 == availableCells.size()) {
                log.warn("No available cells for mob `{}' ({})",
                        mobInfo.getName(),
                        mobInfo.getId()
                );
                continue;
            }

            for (int i = 0; i < maxInWorld; i++) {
                MobAvailableCell mobCell;

                if (1 == availableCells.size()) {
                    mobCell = availableCells.get(0);
                } else {
                    mobCell = availableCells.get(random.nextInt(availableCells.size()));
                }

                int idLayout = mobCell.getIdLayout();
                int x = mobCell.getX();
                int y = mobCell.getY();

                log.info("Placing mob `{}' ({}) on layout {} (x: {}, y: {})",
                        mobInfo.getName(),
                        mobInfo.getId(),
                        idLayout,
                        x,
                        y
                );

                try {
                    Mob mob = new Mob(mobs.size(), mobInfo);
                    layouts.get(idLayout).getCell(x, y).addContent(mob);
                    mobs.put(mob.getId(), mob);
                } catch (BadCoordinatesException e) {
                    log.error("Can't place mob");
                }
            }
        }
    }

    private void initializeNpcs() {
        log.info("Initializing NPCs");

        for (Npc npc : DaoFactory.getInstance().npcDao.getAll()) {
            addNpc(npc);
        }
    }

    public Layout getLayout(int id) {
        return layouts.get(id);
    }

    public static Mob getMobById(int id) {
        return mobs.get(id);
    }

    public static MobInfo getMobInfoById(int id) {
        return mobInfos.get(id);
    }

    public static void removeMobById(int id) {
        Mob mob = mobs.get(id);
        Cell cell = mob.getCell();

        cell.removeContent(mob);
        mobs.remove(id);

        Online.notifier.notifyByCharacter(
                cell.getCharacters(),
                new Response(true, true, "cell-update").appendData("cell", cell));
    }

    public static void addMobInfo(MobInfo mobInfo) {
        mobInfos.put(mobInfo.getId(), mobInfo);
    }

    public static void deleteMobInfo(MobInfo mobInfo) {
        mobInfos.remove(mobInfo.getId());
    }

    public static void addNpc(Npc npc) {
        log.info("Placing NPC `{}' ({}) on layout {} (x: {}, y: {})",
                npc.getName(),
                npc.getId(),
                npc.getIdLayout(),
                npc.getX(),
                npc.getY()
        );

        try {
            layouts.get(npc.getIdLayout()).getCell(npc.getX(), npc.getY()).addContent(npc);
            npcs.put(npc.getId(), npc);
        } catch (BadCoordinatesException e) {
            log.error("Can't place npc");
        }
    }

    public static Npc getNpcById(int id) {
        return npcs.get(id);
    }

    public static void deleteNpc(Npc npc) {
        try {
            layouts.get(npc.getIdLayout()).getCell(npc.getX(), npc.getY()).removeContent(npc);
        } catch (BadCoordinatesException e) {
            e.printStackTrace();
        }

        npcs.remove(npc.getId());
    }
}
