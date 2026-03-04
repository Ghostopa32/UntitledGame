package org.example;

import java.awt.*;

public class Background {

    // Цвета
    private static final Color SKY_COLOR = new Color(200, 220, 240);
    private static final Color GROUND_COLOR = new Color(220, 240, 220);
    private static final Color ROAD_COLOR = new Color(160, 120, 70);
    private static final Color GRASS_COLOR = new Color(60, 150, 60);
    private static final Color DIRT_COLOR = new Color(100, 70, 30);
    private static final Color TRUNK_COLOR = new Color(100, 60, 30);
    private static final Color PINE_GREEN_1 = new Color(30, 100, 30);
    private static final Color PINE_GREEN_2 = new Color(55, 125, 55);
    private static final Color PINE_GREEN_3 = new Color(80, 150, 80);
    private static final Color LEAF_GREEN_1 = new Color(70, 160, 60);
    private static final Color LEAF_GREEN_2 = new Color(50, 130, 50);

    /**
     * Отрисовка всего фона: небо, земля, облака, дорога, трава, деревья, камень.
     * Всё рисуется детерминировано — одинаково при каждом вызове.
     */
    public void draw(Graphics2D g, int panelWidth, int panelHeight) {
        int baseY = panelHeight * 2 / 3;

        // 1. Небо и земля
        g.setColor(SKY_COLOR);
        g.fillRect(0, 0, panelWidth, baseY);
        g.setColor(GROUND_COLOR);
        g.fillRect(0, baseY, panelWidth, panelHeight - baseY);

        // 2. Облака
        drawClouds(g, panelWidth, baseY);

        // 3. Дорога
        drawRoad(g, panelWidth, baseY);

        // 4. Трава (фиксированная сетка)
        drawGrass(g, panelWidth, baseY, panelHeight);

        // 5. Деревья
        drawTrees(g, baseY);

        // 6. Камень
        drawStone(g, panelWidth, baseY);
    }

    private void drawClouds(Graphics2D g, int width, int baseY) {
        g.setColor(new Color(240, 248, 255));
        // Облако 1
        g.fillOval(50, 30, 70, 50);
        g.fillOval(80, 20, 60, 50);
        g.fillOval(100, 30, 70, 40);
        // Облако 2
        int centerX = width / 2;
        g.fillOval(centerX - 100, 50, 80, 50);
        g.fillOval(centerX - 70, 40, 70, 50);
        g.fillOval(centerX - 40, 50, 80, 45);
        // Облако 3
        g.fillOval(width - 150, 40, 75, 55);
        g.fillOval(width - 120, 30, 65, 50);
        g.fillOval(width - 90, 40, 80, 45);

        // Контуры
        g.setColor(new Color(200, 220, 240));
        g.setStroke(new BasicStroke(1));
        g.drawOval(50, 30, 70, 50);
        g.drawOval(80, 20, 60, 50);
        g.drawOval(100, 30, 70, 40);
        g.drawOval(centerX - 100, 50, 80, 50);
        g.drawOval(centerX - 70, 40, 70, 50);
        g.drawOval(centerX - 40, 50, 80, 45);
        g.drawOval(width - 150, 40, 75, 55);
        g.drawOval(width - 120, 30, 65, 50);
        g.drawOval(width - 90, 40, 80, 45);
    }

    private void drawRoad(Graphics2D g, int width, int baseY) {
        g.setColor(ROAD_COLOR);
        int h = 100;
        int[] x = {0, width/5, width/3, width/2, 2*width/3, 4*width/5, width, width, 4*width/5, 2*width/3, width/2, width/3, width/5, 0};
        int[] y = {baseY, baseY+25, baseY-15, baseY+10, baseY-10, baseY+15, baseY, baseY+h, baseY+h-15, baseY+h+10, baseY+h-10, baseY+h+15, baseY+h-25, baseY+h};
        g.fillPolygon(x, y, x.length);
        g.setColor(Color.BLACK);
        g.drawPolygon(x, y, x.length);

        // Грязевые пятна — фиксированные позиции
        g.setColor(DIRT_COLOR);
        g.fillOval(width/2 - 40, baseY + 30, 12, 12);
        g.fillOval(width/2 + 10, baseY + 45, 12, 12);
        g.fillOval(width/2 - 20, baseY + 60, 12, 12);
        g.fillOval(width/2 + 30, baseY + 20, 12, 12);
        g.fillOval(width/2 - 10, baseY + 50, 12, 12);
        g.fillOval(width/2 + 45, baseY + 35, 12, 12);
    }

    private void drawGrass(Graphics2D g, int width, int baseY, int height) {
        g.setColor(GRASS_COLOR);
        g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Фиксированная сетка травы — без рандома
        for (int gx = 15; gx < width; gx += 35) {
            for (int gy = baseY + 10; gy < height - 10; gy += 45) {
                // 3 фиксированных стебля с разными углами
                drawGrassBlade(g, gx, gy, -25, 14);
                drawGrassBlade(g, gx, gy, 0, 16);
                drawGrassBlade(g, gx, gy, 25, 13);
            }
        }
    }

    private void drawGrassBlade(Graphics2D g, int x, int y, int angleDeg, int len) {
        double angle = Math.toRadians(angleDeg);
        int dx = (int) (Math.cos(angle) * 2);
        int dy = (int) (Math.sin(angle) * 2);
        int x1 = x + dx;
        int y1 = y;
        int x2 = x1 + (int) (Math.cos(angle) * len);
        int y2 = y1 - (int) (Math.sin(angle) * len);
        g.drawLine(x1, y1, x2, y2);

        // Листик
        double leafAngle = angle + Math.toRadians(-10);
        int lx = x2 + (int) (Math.cos(leafAngle) * 3);
        int ly = y2 - (int) (Math.sin(leafAngle) * 3);
        g.fillOval(lx - 2, ly - 1, 4, 2);
    }

    private void drawTrees(Graphics2D g, int baseY) {
        drawPineTree(g, 100, baseY - 45);
        drawLeafTree(g, 250, baseY - 45);
        drawPineTree(g, 600, baseY - 45);
        drawLeafTree(g, 750, baseY - 45);
    }

    private void drawPineTree(Graphics2D g, int x, int y) {
        int size = 45;
        // Тень
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x - 12, y + size/2 + 8, size + 15, 8);
        // Ствол
        g.setColor(TRUNK_COLOR);
        g.fillRect(x + size/2 - 4, y + size/2 - 35, 8, 35);
        // Крона — 3 уровня
        for (int level = 0; level < 3; level++) {
            int h = 22 - level * 6;
            int w = 42 - level * 12;
            int treeY = y + size/2 - h * (level + 1) - 10;
            int[] xPoints = {x + size/2 - w/2, x + size/2 + w/2, x + size/2};
            int[] yPoints = {treeY, treeY, treeY - h};
            Color c = level == 0 ? PINE_GREEN_1 : level == 1 ? PINE_GREEN_2 : PINE_GREEN_3;
            g.setColor(c);
            g.fillPolygon(xPoints, yPoints, 3);
            g.setColor(Color.DARK_GRAY);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }

    private void drawLeafTree(Graphics2D g, int x, int y) {
        int size = 45;
        // Тень
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(x - 12, y + size/2 + 8, size + 15, 8);
        // Ствол
        g.setColor(TRUNK_COLOR);
        g.fillRect(x + size/2 - 3, y + size/2 - 30, 6, 30);
        // Крона — 2 слоя
        g.setColor(LEAF_GREEN_1);
        g.fillOval(x - size/2 - 6, y - size/2 - 18, size + 12, size + 12);
        g.setColor(LEAF_GREEN_2);
        g.fillOval(x - size/2, y - size/2, size, size);
    }

    private void drawStone(Graphics2D g, int width, int baseY) {
        int centerX = width / 2;
        int barnWidth = 80;
        int stoneX = centerX + barnWidth/2 + 50;
        int stoneY = baseY - 80;

        int[] stonePolygonX = {stoneX + 10, stoneX + 65, stoneX + 80, stoneX + 70, stoneX + 60, stoneX + 40, stoneX + 20, stoneX};
        int[] stonePolygonY = {stoneY + 20, stoneY + 10, stoneY + 30, stoneY + 55, stoneY + 65, stoneY + 60, stoneY + 50, stoneY + 35};

        GradientPaint gradient = new GradientPaint(stoneX, stoneY, new Color(140, 140, 140), stoneX + 80, stoneY + 70, new Color(100, 100, 100));
        g.setPaint(gradient);
        g.fillPolygon(stonePolygonX, stonePolygonY, 8);

        g.setColor(new Color(80, 80, 80));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(stonePolygonX, stonePolygonY, 8);

        // Текстура камня
        g.setColor(new Color(90, 90, 90));
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(stoneX + 20, stoneY + 25, stoneX + 35, stoneY + 30);
        g.drawLine(stoneX + 45, stoneY + 20, stoneX + 55, stoneY + 28);
        g.drawLine(stoneX + 30, stoneY + 45, stoneX + 40, stoneY + 50);

        // Тень
        g.setColor(new Color(70, 70, 70, 80));
        int[] shadowX = {stoneX + 5, stoneX + 75, stoneX + 70, stoneX + 10};
        int[] shadowY = {stoneY + 70, stoneY + 75, stoneY + 85, stoneY + 80};
        g.fillPolygon(shadowX, shadowY, 4);
    }
}