package shukaro.nodalmechanics.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import shukaro.nodalmechanics.items.NodalItems;

public class NodalTab extends CreativeTabs
{
    public NodalTab(String label)
    {
        super(label);
    }

    @Override
    public Item getTabIconItem()
    {
        return NodalItems.itemMatrix;
    }
}
