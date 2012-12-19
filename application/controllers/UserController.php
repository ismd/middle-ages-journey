<?php
/**
 * Контроллер пользователя
 *
 * @author ismd
 */

class UserController extends AbstractAuthController {

    public function index() {
        $this->redirect('/');
    }

    /**
     * Список персонажей игрока
     */
    public function characters() {
        $this->view->setLayout('empty');

        $characters = $this->session->user->characters;

        // Преобразуем в массив каждого персонажа
        foreach ($characters as $i => $character) {
            $characters[$i] = $character->toArray();
        }

        $this->view->characters = $characters;
    }
}
