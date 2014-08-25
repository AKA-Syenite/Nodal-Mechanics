package shukaro.nodalmechanics.recipe;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import shukaro.nodalmechanics.NodalMechanics;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;

public class RecipeAttune implements IArcaneRecipe
{
    private ItemStack output;

    @Override
    public boolean matches(IInventory inventory, World world, EntityPlayer player)
    {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), getResearch()))
            return false;
        ItemStack matrix = null;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                ItemStack slotStack = ThaumcraftApiHelper.getStackInRowAndColumn(inventory, i, j);
                if (matrix != null && checkItemEquals(slotStack, new ItemStack(NodalItems.itemMatrix)))
                    return false;
                else if (checkItemEquals(slotStack, new ItemStack(NodalItems.itemMatrix)))
                    matrix = slotStack;
            }
        }
        if (matrix == null)
            return false;
        TMap<String, Integer> aspectMap = new THashMap<String, Integer>();
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                ItemStack slotStack = ThaumcraftApiHelper.getStackInRowAndColumn(inventory, i, j);
                if (slotStack == null)
                    continue;
                else if (slotStack.getItem().equals(NodalItems.itemMatrix))
                    continue;
                else if (slotStack.getItem().equals(ItemApi.getItem("itemEssence", 1).getItem()))
                {
                    if (!slotStack.hasTagCompound())
                        return false;
                    else
                    {
                        AspectList list = new AspectList();
                        list.readFromNBT(slotStack.getTagCompound());
                        if (list.size() > 0)
                        {
                            for (Aspect aspect : list.getAspects())
                            {
                                if (aspectMap.containsKey(aspect.getTag()))
                                    aspectMap.put(aspect.getTag(), aspectMap.get(aspect.getTag()) + 1);
                                else
                                    aspectMap.put(aspect.getTag(), 1);
                            }
                        }
                        else
                            return false;
                    }
                }
                else
                    return false;
            }
        }
        if (aspectMap.size() > 0)
        {
            String aspects = "";
            for (String aspect : aspectMap.keySet())
            {
                aspects = aspects + aspect + ",";
            }
            aspects = aspects.substring(0, aspects.length() - 1);
            output = new ItemStack(NodalItems.itemMatrix);
            output.setTagCompound(new NBTTagCompound());
            output.getTagCompound().setString("aspects", aspects);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inventory)
    {
        return output.copy();
    }

    @Override
    public int getRecipeSize()
    {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    @Override
    public AspectList getAspects()
    {
        return new AspectList().add(Aspect.ORDER, 10);
    }

    @Override
    public AspectList getAspects(IInventory inventory)
    {
        return new AspectList().add(Aspect.ORDER, 10);
    }

    @Override
    public String getResearch()
    {
        return "NODECATALYZATION";
    }

    private boolean checkItemEquals(ItemStack target, ItemStack input)
    {
        if (input == null && target != null || input != null && target == null)
        {
            return false;
        }
        return (target.getItem() == input.getItem() &&
                (!target.hasTagCompound() || ItemStack.areItemStackTagsEqual(target, input)) &&
                (target.getItemDamage() == OreDictionary.WILDCARD_VALUE|| target.getItemDamage() == input.getItemDamage()));
    }
}
