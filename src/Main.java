import Excaptions.MaterialManipulationException;
import ObserverPattern.Listeners.Player;
import Sandship.Material;
import Sandship.Warehouse;

public class Main {
    public static void main(String[] args) {
        Player player = new Player();

        Material ironOre = new Material("Iron Ore", "Iron Ore", "ironOre.png", 200);
        Material goldenOre = new Material("Golden Ore", "Golden Ore", "goldenOre.png", 100);

        Warehouse warehouse1 = new Warehouse("warehouse1", player);
        Warehouse warehouse2 = new Warehouse("warehouse2", player);

        int[] ironOresToAddWarehouse1 = {-15, 0, -5, 50, 100, 0, 110, 30};
        for (int ironOreQuantity : ironOresToAddWarehouse1) {
            try {
                warehouse1.addMaterial(ironOre, ironOreQuantity);
            } catch (MaterialManipulationException exc) {
                exc.what();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int[] goldenOresToAddWarehouse2 = {-15, 0, -5, 10, 50, 0, 50, 40};
        for (int goldenOreQuantity : goldenOresToAddWarehouse2) {
            try {
                warehouse2.addMaterial(goldenOre, goldenOreQuantity);
            } catch (MaterialManipulationException exc) {
                exc.what();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        warehouse1.showWarehouseData();
        warehouse2.showWarehouseData();

        int[] ironOresToMoveFromWarehouse1ToWarehouse2 = {-15, 0, 500, 90, 150, 20};
        for (int ironOreQuantity : ironOresToMoveFromWarehouse1ToWarehouse2) {
            try {
                warehouse1.moveMaterial(ironOre, ironOreQuantity, warehouse2);
            } catch (MaterialManipulationException exc) {
                exc.what();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int[] goldenOresToMoveFromWarehouse2ToWarehouse1 = {-15, 0, 500, 60, 60, 40, -20};
        for (int goldenOreQuantity : goldenOresToMoveFromWarehouse2ToWarehouse1) {
            try {
                warehouse2.moveMaterial(goldenOre, goldenOreQuantity, warehouse1);
            } catch (MaterialManipulationException exc) {
                exc.what();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        warehouse1.showWarehouseData();
        warehouse2.showWarehouseData();
    }
}