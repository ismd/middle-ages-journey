<?php
/**
 * Класс вещи
 * @author ismd
 */

class Item extends Abstract_Item {

    /**
     * Тип вещи из таблицы ItemType
     * @var string
     */
    protected $_type;

    /**
     * Атрибуты вещи. Берутся из таблиц ItemAttribute и Attribute
     * @var Attribute[]
     */
    protected $_attributes;

    public function toArray() {
        $attributes = array();
        foreach ($this->getAttributes() as $attribute) {
            $attributes[] = $attribute->toArray();
        }

        return array_merge(
            parent::toArray(),
            array(
                'type'       => $this->getType(),
                'attributes' => $attributes,
            )
        );
    }

    public function setType($value) {
        $this->_type = (string)$value;
        return $this;
    }

    public function getType() {
        return $this->_type;
    }

    public function setAttributes($value) {
        $this->_attributes = (array)$value;
        return $this;
    }

    public function getAttributes() {
        if (null == $this->_attributes) {
            $mapper           = new AttributeMapper;
            $this->attributes = $mapper->getByItem($this);
        }

        return $this->_attributes;
    }
}