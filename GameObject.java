package org.example;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Базовый класс для всех игровых объектов.
 * Предоставляет позицию, размеры, цвет и шаблон отрисовки.
 */
public class GameObject implements Cloneable {

    // ===== Поля =====
    protected int id;                    // Идентификатор объекта
    protected float x, y;                // Позиция (дробная для плавности)
    protected int width, height;         // Размеры (отдельно ширина и высота)
    protected float speed;               // Скорость перемещения
    protected Color color;               // Базовый цвет

    // ===== Конструкторы =====

    /**
     * Пустой конструктор для наследования.
     * Инициализирует значения по умолчанию.
     */
    public GameObject() {
        this.id = -1;
        this.x = 0f;
        this.y = 0f;
        this.width = 50;
        this.height = 50;
        this.speed = 0f;
        this.color = Color.WHITE;
    }

    /**
     * Конструктор с float-координатами и размерами.
     */
    public GameObject(float x, float y, int width, int height) {
        this(-1, x, y, width, height, 0f, Color.WHITE);
    }

    /**
     * Конструктор с float-координатами (квадратный объект).
     */
    public GameObject(float x, float y) {
        this(x, y, 50, 50);
    }

    /**
     * Конструктор с int-координатами (квадратный объект).
     */
    public GameObject(int x, int y) {
        this((float) x, (float) y, 50, 50);
    }

    /**
     * Полный конструктор для расширенной настройки.
     */
    protected GameObject(int id, float x, float y, int width, int height, float speed, Color color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    // ===== Геттеры =====
    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getId() { return id; }
    public float getSpeed() { return speed; }
    public Color getColor() { return color; }

    // ===== Сеттеры =====
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setSpeed(float speed) { this.speed = speed; }
    public void setColor(Color color) { this.color = color; }
    public void setId(int id) { this.id = id; }

    // ===== Вспомогательные методы =====

    /**
     * Возвращает границы объекта для проверки коллизий.
     */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    /**
     * Базовое обновление: движение по скорости вдоль оси X.
     * Переопределяется в подклассах при необходимости.
     */
    public void update() {
        x += speed;
    }

    /**
     * Движение к целевому объекту.
     */
    public void moveTowards(GameObject target) {
        float dirX = target.x - this.x;
        float dirY = target.y - this.y;
        float distance = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        if (distance > 1.0f) {
            float normX = dirX / distance;
            float normY = dirY / distance;
            this.x += normX * speed;
            this.y += normY * speed;
        }
    }

    // ===== Отрисовка =====

    /**
     * Базовый метод отрисовки.
     *
     * Почему его не удаляем:
     * 1. Шаблонный метод: задаёт структуру, подклассы расширяют логику
     * 2. Безопасность: если подкласс забудет переопределить — объект всё равно отобразится
     * 3. Отладка: можно быстро увидеть все GameObject на сцене без доп. кода
     * 4. Расширяемость: в будущем можно добавить общую логику (тени, контуры)
     *
     * @param g графический контекст
     */
    public void draw(Graphics g) {
        // Базовая отрисовка прямоугольника (для отладки и простых объектов)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fill(new Rectangle2D.Float(x, y, width, height));
        g2d.setColor(Color.BLACK);
        g2d.draw(new Rectangle2D.Float(x, y, width, height));
    }

    /**
     * Публичный метод для вызова из игрового цикла.
     * Может быть расширен логикой видимости, трансформаций и т.д.
     */
    public void render(Graphics g) {
        draw(g);
    }

    // ===== Стандартные методы =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return id == that.id &&
                Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                width == that.width &&
                height == that.height &&
                Float.compare(that.speed, speed) == 0 &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, width, height, speed, color);
    }

    @Override
    public GameObject clone() {
        try {
            return (GameObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}