package Sandship;

import Excaptions.MaterialManipulationException;
import Excaptions.MaterialManipulationExceptionType;
import ObserverPattern.EventManager;
import ObserverPattern.EventType;
import ObserverPattern.Listeners.Player;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    EventManager eventManager = new EventManager();
    private final Map<Material, Integer> materials = new HashMap<>();
    private final String name;

    public Warehouse(String name, Player player) {
        this.name = name;
        eventManager.subscribe(EventType.MATERIAL_ADDED, player);
        // eventManager.unsubscribe(EventType.MATERIAL_ADDED, player);

        eventManager.subscribe(EventType.MATERIAL_REMOVED, player);
        eventManager.subscribe(EventType.MATERIAL_LEFT, player);
    }

    // returns the quantity of the materials which are left, and can't be added
    public int addMaterial(Material material, int appendingQuantity) throws MaterialManipulationException {
        if(appendingQuantity < 0) {
            throw new MaterialManipulationException(MaterialManipulationExceptionType.NEGATIVE_QUANTITY);
        }

        if(appendingQuantity == 0) {
            return 0;
        }

        int presentMaterialQuantity = materialQuantity(material);
        int maxMaterialCountThatCanBeAdded = material.getMaxCapacity() - presentMaterialQuantity;

        // at first check usual cases
        if(maxMaterialCountThatCanBeAdded >= appendingQuantity) {
            eventManager.notify(EventType.MATERIAL_ADDED, appendingQuantity + " " + material.getName() + "(s) added in " + name);
            materials.put(material, presentMaterialQuantity + appendingQuantity);
            return 0;
        }

        if (maxMaterialCountThatCanBeAdded > 0) {
            eventManager.notify(EventType.MATERIAL_ADDED, maxMaterialCountThatCanBeAdded + " " + material.getName() + "(s) added in " + name);
            materials.put(material, presentMaterialQuantity + maxMaterialCountThatCanBeAdded);

            int leftMaterialsQuantity = appendingQuantity - maxMaterialCountThatCanBeAdded;
            eventManager.notify(EventType.MATERIAL_LEFT, leftMaterialsQuantity + " " + material.getName() + "(s) can't be added to " + name);
            return leftMaterialsQuantity;
        }

        eventManager.notify(EventType.MATERIAL_LEFT, appendingQuantity + " " + material.getName() + "(s) can't be added to " + name);
        return appendingQuantity;
    }

    public void removeMaterial(Material material, int deletingQuantity) throws MaterialManipulationException {

        if(deletingQuantity < 0) {
            throw new MaterialManipulationException(MaterialManipulationExceptionType.NEGATIVE_QUANTITY);
        }

        if(deletingQuantity == 0) {
            return;
        }

        int presentMaterialQuantity = materialQuantity(material);

        // first check common cases
        if(presentMaterialQuantity >= deletingQuantity) {
            eventManager.notify(EventType.MATERIAL_REMOVED, deletingQuantity + " " + material.getName() + "(s) removed from " + name);
            materials.put(material, presentMaterialQuantity - deletingQuantity);
            return;
        }

        /*
            we can handle this case by removing materials partially
            but in real cases player could not be able to remove more materials, than he has
            is this a bug, or player is cheating?
         */
        throw new MaterialManipulationException(MaterialManipulationExceptionType.INSUFFICIENT_QUANTITY);
    }

    public void moveMaterial(Material material, int movingQuantity, Warehouse toWarehouse) throws MaterialManipulationException {

        int presentMaterialQuantity = materialQuantity(material);

        if(presentMaterialQuantity < movingQuantity) {
            /*
                we can handle this case by moving materials partially
                but in real cases player could not be able to move more materials, than he has
                is this a bug, or player is cheating?
             */
            throw new MaterialManipulationException(MaterialManipulationExceptionType.INSUFFICIENT_QUANTITY);
        }

        int materialsMovedQuantity = movingQuantity - toWarehouse.addMaterial(material, movingQuantity);
        removeMaterial(material, materialsMovedQuantity);
    }

    public int materialQuantity(Material material) {
        if(materials.containsKey(material)) {
            return materials.get(material);
        }
        return 0;
    }

    public void showWarehouseData() {
        System.out.println("--------------- " + name);
        for(Map.Entry<Material, Integer> entry : materials.entrySet()) {
            Material material = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(material.getName() + " - " + quantity + "/" + material.getMaxCapacity());
        }
        System.out.println("---------------");
    }
}
