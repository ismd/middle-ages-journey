<?php
/**
 * Главная страница
 * @author ismd
 */

class IndexController extends PsController {

    public function index() {
        $this->view->controller = $this->registry->router->getController();

        $config = PsConfig::getInstance()->config->websocket;
        $this->view->ws = [
            'host' => $config->host,
            'port' => $config->port,
        ];
    }
}
