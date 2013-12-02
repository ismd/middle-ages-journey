package game.dao;

import com.google.common.collect.Lists;
import game.chat.ChatMessage;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author ismd
 */
public class ChatMessageDao extends Dao {

    private final int timeout = 60 * 60 * 1000;

    ChatMessageDao() {
    }

    public List<ChatMessage> getLastMessages() {
        Session session = sessionFactory.openSession();
        List<ChatMessage> messages = session.createCriteria(ChatMessage.class)
                .add(Restrictions.gt("sended", new Date(new Date().getTime() - timeout)))
                .addOrder(Order.desc("sended"))
                .setMaxResults(15)
                .list();

        session.close();
        return Lists.reverse(messages);
    }
}
