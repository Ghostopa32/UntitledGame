package org.example;

import java.awt.*;

/**
 * Класс вражеской башни.
 * Наследуется от GameObject, использует его систему координат.
 *
 * Здоровье:
 * - MAX: 200 >= health >= 150 (зелёный)
 * - MID: 150 > health >= 70 (жёлтый)
 * - LOW: 70 > health >= 0 (красный)
 */
public class EnemyTower extends GameObject {

    // ===== Типы башен =====
    public enum TowerType {
        RED("Красная башня", new Color(203, 10, 36)),
        BLUE("Синяя башня", new Color(36, 10, 203)),
        GREEN("Зелёная башня", new Color(10, 203, 36));

        private final String name;
        private final Color baseColor;

        TowerType(String name, Color baseColor) {
            this.name = name;
            this.baseColor = baseColor;
        }

        public Color getBaseColor() { return baseColor; }
        public String getName() { return name; }
    }

    // ===== Поля =====
    private int health = 200;
    private TowerType type = TowerType.RED;
    private int xOffset = 0;  // Смещение по оси X относительно базовой позиции

    // ===== Конструкторы =====

    /**
     * Базовый конструктор.
     * @param x центр башни по горизонтали
     * @param y уровень земли (основание башни)
     */
    public EnemyTower(float x, float y) {
        super(x, y, 120, 300); // width=120, height=300
        this.health = 200;
    }

    /**
     * Конструктор с типом башни и здоровьем.
     */
    public EnemyTower(float x, float y, TowerType type, int health) {
        super(x, y, 120, 300);
        this.type = type != null ? type : TowerType.RED;
        this.health = Math.max(0, Math.min(200, health));
    }

    /**
     * Конструктор со смещением по X.
     * @param xOffset дополнительное смещение от базовой позиции x
     */
    public EnemyTower(float x, float y, int xOffset) {
        super(x, y, 120, 300);
        this.xOffset = xOffset;
    }

    /**
     * Дефолтный конструктор.
     */
    public EnemyTower() {
        super(0, 0, 120, 300);
    }

    // ===== Геттеры и сеттеры =====
    public int getHealth() { return health; }
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(200, health));
    }

    public TowerType getType() { return type; }
    public void setType(TowerType type) { this.type = type; }

    public int getXOffset() { return xOffset; }
    public void setXOffset(int xOffset) { this.xOffset = xOffset; }

    /**
     * Возвращает текущий цвет башни в зависимости от здоровья.
     */
    private Color getHealthColor() {
        if (health >= 150) {
            return type.getBaseColor(); // MAX — нормальный цвет
        } else if (health >= 70) {
            return type.getBaseColor().brighter(); // MID — светлее
        } else {
            return Color.RED.darker(); // LOW — тревожный красный
        }
    }

    // ===== Отрисовка =====

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        // Константы размеров
        final int towerHeight = 300;
        final int baseWidth = 120;
        final int topWidth = 80;

        // Координаты из GameObject + смещение
        int centerX = (int)(x + xOffset);  // ИСПРАВЛЕНО: явное приведение типа
        int baseY = (int)y;                // ИСПРАВЛЕНО: явное приведение типа

        // ===== КОРПУС БАШНИ =====
        int[] towerX = {
                centerX - baseWidth / 2,           // левый низ
                centerX + baseWidth / 2,           // правый низ
                centerX + topWidth / 2,            // правый верх (начало сужения)
                centerX + topWidth / 2,            // правый верх зубцов
                centerX + topWidth / 2 - 10,       // выступ справа
                centerX + topWidth / 2 - 10,       // правая сторона крыши
                centerX,                           // вершина крыши
                centerX - topWidth / 2 + 10,       // левая сторона крыши
                centerX - topWidth / 2 + 10,       // выступ слева
                centerX - topWidth / 2,            // левый верх зубцов
                centerX - topWidth / 2             // левый верх
        };

        int[] towerY = {
                baseY,                              // низ
                baseY,                              // низ
                baseY - towerHeight + 100,          // начало сужения
                baseY - towerHeight + 50,           // верх основной части
                baseY - towerHeight + 15,           // выступ
                baseY - towerHeight + 100,          // верх зубцов
                baseY - towerHeight,                // вершина
                baseY - towerHeight + 100,          // верх зубцов
                baseY - towerHeight + 15,           // выступ
                baseY - towerHeight + 50,           // верх основной части
                baseY - towerHeight + 100           // начало сужения
        };

        // Заливка башни с учётом здоровья
        g2d.setColor(getHealthColor());
        g2d.fillPolygon(towerX, towerY, 11);

        // Обводка башни
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(towerX, towerY, 11);

        // ===== ОКНО =====
        int windowY = baseY - towerHeight + 150;
        int[] windowX = {centerX - 15, centerX + 15, centerX + 15, centerX - 15};
        int[] windowYCoords = {windowY, windowY, windowY + 30, windowY + 30};

        g2d.setColor(new Color(216, 20, 20));
        g2d.fillPolygon(windowX, windowYCoords, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(windowX, windowYCoords, 4);

        // Переплет окна
        g2d.drawLine(centerX, windowY, centerX, windowY + 30);
        g2d.drawLine(centerX - 15, windowY + 15, centerX + 15, windowY + 15);

        // ===== ДВЕРЬ =====
        int doorWidth = 40;
        int doorHeight = 60;
        int[] doorX = {
                centerX - doorWidth / 2,
                centerX + doorWidth / 2,
                centerX + doorWidth / 2,
                centerX - doorWidth / 2
        };
        int[] doorYCoords = {
                baseY - doorHeight,
                baseY - doorHeight,
                baseY,
                baseY
        };

        g2d.setColor(new Color(58, 9, 12));
        g2d.fillPolygon(doorX, doorYCoords, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(doorX, doorYCoords, 4);

        // Ручка двери
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(centerX + doorWidth / 2 - 10, baseY - doorHeight / 2, 6, 6);

        // ===== ГЛАЗ НА ВЕРШИНЕ (ромб + круг) =====
        int eyePoleHeight = 100;
        int eyePoleWidth = 60;
        int eyeCenterX = centerX;
        int eyeTopY = baseY - towerHeight - eyePoleHeight / 2;

        // Ромб
        int[] eyeX = {
                eyeCenterX,                        // верх
                eyeCenterX + eyePoleWidth / 2,     // право
                eyeCenterX,                        // низ
                eyeCenterX - eyePoleWidth / 2      // лево
        };
        int[] eyeY = {
                eyeTopY,                           // верх
                eyeTopY + eyePoleHeight / 2,       // право
                eyeTopY + eyePoleHeight,           // низ
                eyeTopY + eyePoleHeight / 2        // лево
        };

        g2d.setColor(Color.ORANGE);
        g2d.fillPolygon(eyeX, eyeY, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(eyeX, eyeY, 4);

        // Круг в центре ромба
        int circleDiameter = Math.min(eyePoleWidth, eyePoleHeight) / 2;
        int circleX = eyeCenterX - circleDiameter / 2;
        int circleY = eyeTopY + eyePoleHeight / 2 - circleDiameter / 2;

        g2d.setColor(Color.RED);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(circleX, circleY, circleDiameter, circleDiameter);

        // Вертикальная линия в круге
        int lineOffset = circleDiameter / 5;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(
                circleX + circleDiameter / 2, circleY + lineOffset,
                circleX + circleDiameter / 2, circleY + circleDiameter - lineOffset
        );

        // ===== ИНДИКАТОР ЗДОРОВЬЯ (полоска над башней) =====
        drawHealthBar(g2d, centerX, baseY - towerHeight - 20);
    }

    /**
     * Отрисовка полоски здоровья над башней.
     */
    private void drawHealthBar(Graphics2D g2d, int centerX, int y) {
        int barWidth = 100;
        int barHeight = 10;
        int healthWidth = (int) (barWidth * ((float) health / 200));

        // Фон полоски
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(centerX - barWidth / 2, y, barWidth, barHeight);

        // Текущее здоровье
        if (health >= 150) {
            g2d.setColor(Color.GREEN);
        } else if (health >= 70) {
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.fillRect(centerX - barWidth / 2, y, healthWidth, barHeight);

        // Обводка
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(centerX - barWidth / 2, y, barWidth, barHeight);

        // Текст со значением здоровья
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(Color.WHITE);
        String healthText = health + " HP";
        FontMetrics fm = g2d.getFontMetrics();
        int textX = centerX - fm.stringWidth(healthText) / 2;
        int textY = y - 3;
        g2d.drawString(healthText, textX, textY);
    }
}