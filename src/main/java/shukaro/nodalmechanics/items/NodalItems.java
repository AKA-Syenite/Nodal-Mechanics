package shukaro.nodalmechanics.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class NodalItems
{
    public static ItemMatrix itemMatrix;

    public static void initItems()
    {
        itemMatrix = new ItemMatrix();
        GameRegistry.registerItem(itemMatrix, itemMatrix.getUnlocalizedName());
    }
}
