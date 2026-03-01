package org.example;

import java.awt.*;

/**
 * Класс сарая — визуальный игровой объект с детализированной отрисовкой.
 * Наследуется от GameObject, использует его систему координат.
 */
public class Barn extends GameObject {

    private int health = 200;

    // ===== Конструкторы =====

    /**
     * Создание сарая с позицией и здоровьем по умолчанию.
     * @param x горизонтальный центр сарая
     * @param y уровень земли (основание сарая)
     */
    public Barn(float x, float y) {
        super(x, y, 250, 200); // width=250, height=200
        this.health = 200;
    }

    /**
     * Создание сарая с кастомным здоровьем.
     */
    public Barn(float x, float y, int health) {
        super(x, y, 250, 200);
        this.health = health;
    }

    /**
     * Дефолтный конструктор (позиция 0,0).
     */
    public Barn() {
        super(0, 0, 250, 200);
    }

    // ===== Геттеры и сеттеры =====
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    // ===== Отрисовка =====

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        // Константы размеров
        final int barnHeight = 200;
        final int barnWidth = 250;
        final int roofHeight = 50;

        // Координаты из GameObject
        int centerX = (int)x;  // ИСПРАВЛЕНО: явное приведение типа float к int
        int baseY = (int)y;    // ИСПРАВЛЕНО: явное приведение типа float к int

        // ===== ОБЛАКА =====
        g2d.setColor(new Color(240, 248, 255));

        // Облако 1 (слева)
        g2d.fillOval(centerX - 350, 30, 70, 50);
        g2d.fillOval(centerX - 320, 20, 60, 50);
        g2d.fillOval(centerX - 300, 30, 70, 40);

        // Облако 2 (по центру)
        g2d.fillOval(centerX - 100, 50, 80, 50);
        g2d.fillOval(centerX - 70, 40, 70, 50);
        g2d.fillOval(centerX - 40, 50, 80, 45);

        // Облако 3 (справа)
        g2d.fillOval(centerX + 250, 40, 75, 55);
        g2d.fillOval(centerX + 280, 30, 65, 50);
        g2d.fillOval(centerX + 310, 40, 80, 45);

        // Контуры облаков
        g2d.setColor(new Color(200, 220, 240));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(centerX - 350, 30, 70, 50);
        g2d.drawOval(centerX - 320, 20, 60, 50);
        g2d.drawOval(centerX - 300, 30, 70, 40);
        g2d.drawOval(centerX - 100, 50, 80, 50);
        g2d.drawOval(centerX - 70, 40, 70, 50);
        g2d.drawOval(centerX - 40, 50, 80, 45);
        g2d.drawOval(centerX + 250, 40, 75, 55);
        g2d.drawOval(centerX + 280, 30, 65, 50);
        g2d.drawOval(centerX + 310, 40, 80, 45);

        // ===== КАМЕНЬ справа от сарая =====
        g2d.setStroke(new BasicStroke(2));
        int stoneX = centerX + barnWidth / 2 + 50;
        int stoneY = baseY - 80;

        int[] stonePolygonX = {
                stoneX + 10, stoneX + 65, stoneX + 80, stoneX + 70,
                stoneX + 60, stoneX + 40, stoneX + 20, stoneX
        };
        int[] stonePolygonY = {
                stoneY + 20, stoneY + 10, stoneY + 30, stoneY + 55,
                stoneY + 65, stoneY + 60, stoneY + 50, stoneY + 35
        };

        GradientPaint stoneGradient = new GradientPaint(
                stoneX, stoneY, new Color(140, 140, 140),
                stoneX + 80, stoneY + 70, new Color(100, 100, 100)
        );
        g2d.setPaint(stoneGradient);
        g2d.fillPolygon(stonePolygonX, stonePolygonY, 8);

        g2d.setColor(new Color(80, 80, 80));
        g2d.drawPolygon(stonePolygonX, stonePolygonY, 8);

        // Текстура камня
        g2d.setColor(new Color(90, 90, 90));
        g2d.setStroke(new BasicStroke(1.5f));

        // Тень под камнем
        g2d.setColor(new Color(70, 70, 70, 80));
        int[] shadowX = { stoneX + 5, stoneX + 75, stoneX + 70, stoneX + 10 };
        int[] shadowY = { stoneY + 70, stoneY + 75, stoneY + 85, stoneY + 80 };
        g2d.fillPolygon(shadowX, shadowY, 4);

        // ===== САРАЙ (основная форма) =====
        int[] barnX = {
                centerX - barnWidth / 2,
                centerX + barnWidth / 2,
                centerX + barnWidth / 2,
                centerX + barnWidth / 2 - 30,
                centerX,
                centerX - barnWidth / 2 + 30,
                centerX - barnWidth / 2
        };
        int[] barnY = {
                baseY,
                baseY,
                baseY - barnHeight,
                baseY - barnHeight - roofHeight,
                baseY - barnHeight - roofHeight - 20,
                baseY - barnHeight - roofHeight,
                baseY - barnHeight
        };

        g2d.setColor(new Color(180, 140, 100));
        g2d.fillPolygon(barnX, barnY, 7);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(barnX, barnY, 7);

        // ===== ВОРОТА =====
        int gateWidth = 120;
        int gateHeight = 140;
        int[] gateX = {
                centerX - gateWidth / 2,
                centerX + gateWidth / 2,
                centerX + gateWidth / 2,
                centerX - gateWidth / 2
        };
        int[] gateY = {
                baseY - gateHeight,
                baseY - gateHeight,
                baseY,
                baseY
        };

        g2d.setColor(new Color(120, 80, 40));
        g2d.fillPolygon(gateX, gateY, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(gateX, gateY, 4);

        // Поперечины на воротах
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(centerX - gateWidth / 2 + 10, baseY - gateHeight / 3,
                centerX + gateWidth / 2 - 10, baseY - gateHeight / 3);
        g2d.drawLine(centerX - gateWidth / 2 + 10, baseY - 2 * gateHeight / 3,
                centerX + gateWidth / 2 - 10, baseY - 2 * gateHeight / 3);

        // ===== КАЛИТКА =====
        int smallDoorWidth = 35;
        int smallDoorHeight = 60;
        int[] smallDoorX = {
                centerX + gateWidth / 4 - smallDoorWidth / 2,
                centerX + gateWidth / 4 + smallDoorWidth / 2,
                centerX + gateWidth / 4 + smallDoorWidth / 2,
                centerX + gateWidth / 4 - smallDoorWidth / 2
        };
        int[] smallDoorY = {
                baseY - smallDoorHeight,
                baseY - smallDoorHeight,
                baseY,
                baseY
        };

        g2d.setColor(new Color(100, 60, 30));
        g2d.fillPolygon(smallDoorX, smallDoorY, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(smallDoorX, smallDoorY, 4);

        // Ручка на калитке
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(centerX + gateWidth / 4 + smallDoorWidth / 2 - 8,
                baseY - smallDoorHeight / 2, 6, 6);

        // ===== ОКНО (по центру, поднято выше) =====
        int windowY = baseY - barnHeight + 10;
        int windowWidth = 60;
        int windowHeight = 50;
        int windowCenterX = centerX;

        int[] windowX = {
                windowCenterX - windowWidth / 2,
                windowCenterX + windowWidth / 2,
                windowCenterX + windowWidth / 2,
                windowCenterX - windowWidth / 2
        };
        int[] windowYCoords = {
                windowY, windowY,
                windowY + windowHeight, windowY + windowHeight
        };

        g2d.setColor(new Color(200, 230, 255));
        g2d.fillPolygon(windowX, windowYCoords, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(windowX, windowYCoords, 4);

        // Переплет окна
        g2d.drawLine(windowCenterX, windowY, windowCenterX, windowY + windowHeight);
        g2d.drawLine(windowCenterX - windowWidth / 2, windowY + windowHeight / 2,
                windowCenterX + windowWidth / 2, windowY + windowHeight / 2);

        // ===== ФЛАГ =====
        int flagPoleX = centerX;
        int flagPoleTopY = baseY - barnHeight - roofHeight - 50;

        // Флагшток
        g2d.setColor(new Color(101, 67, 33));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(flagPoleX, baseY - barnHeight - roofHeight,
                flagPoleX, flagPoleTopY);

        // Флажок
        int[] flagX = { flagPoleX, flagPoleX + 35, flagPoleX };
        int[] flagY = { flagPoleTopY + 8, flagPoleTopY - 10, flagPoleTopY + 25 };

        g2d.setColor(Color.RED);
        g2d.fillPolygon(flagX, flagY, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(flagX, flagY, 3);

        // Украшение на вершине флагштока
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(flagPoleX - 4, flagPoleTopY - 4, 8, 8);
    }
}