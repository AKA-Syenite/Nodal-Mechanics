package shukaro.nodalmechanics.recipe;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;

import java.util.ArrayList;
import java.util.Random;

public class RecipeNode extends InfusionRecipe
{
    public static final int VISMULTIPLIER = 10;
    public static final int INSTABILITY = 10;
    public static Random rand;

    public RecipeNode()
    {
        super("NODECATALYZATION", ItemApi.getItem("itemJarNode", 0), INSTABILITY, new AspectList(), new ItemStack(NodalItems.itemMatrix), new ItemStack[]{ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14)});
        rand = new Random();
    }

    @Override
    public boolean matches(ArrayList<ItemStack> input, ItemStack central, World world, EntityPlayer player)
    {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), getResearch()))
            return false;
        if (input == null || input.size() != 2)
            return false;
        if (!checkItemEquals(input.get(0), getComponents()[0]) || !checkItemEquals(input.get(1), getComponents()[1]))
            return false;
        ((ItemStack)this.recipeOutput).setTagCompound(new NBTTagCompound());
        if (central != null && central.getItem().equals(NodalItems.itemMatrix))
        {
            if (central.hasTagCompound() && central.getTagCompound().hasKey("aspects"))
            {
                TMap<String, Integer> aspectMap = new THashMap<String, Integer>();
                for (String s : central.getTagCompound().getString("aspects").split(","))
                {
                    if (!aspectMap.containsKey(s))
                        aspectMap.put(s, 1);
                    else
                        aspectMap.put(s, aspectMap.get(s) + 1);
                }
                this.aspects = new AspectList();
                AspectList smallAspects = new AspectList();
                for (String s : aspectMap.keySet())
                {
                    this.aspects.add(Aspect.getAspect(s), aspectMap.get(s) * 15 * VISMULTIPLIER);
                    smallAspects.add(Aspect.getAspect(s), aspectMap.get(s) * 15);
                }
                smallAspects.writeToNBT(((ItemStack)this.recipeOutput).getTagCompound());
                ((ItemStack)this.recipeOutput).getTagCompound().setInteger("nodetype", getNodeType());
                if (rand.nextInt(100) < 60)
                    ((ItemStack)this.recipeOutput).getTagCompound().setInteger("nodemod", getNodeMod());
                return true;
            }
        }
        return false;
    }

    private int getNodeType()
    {
        int roll = rand.nextInt(100);
        if (roll < 80)
            return 0;
        else if (roll < 90)
        {
            if (roll < 85)
                return 1;
            else if (roll < 90)
                return 4;
        }
        else
        {
            if (roll < 93)
                return 2;
            else if (roll < 96)
                return 3;
            else
                return 5;
        }
        return 0;
    }

    private int getNodeMod()
    {
        int roll = rand.nextInt(50);
        if (roll < 20)
            return 0;
        else if (roll < 40)
            return 1;
        else
            return 2;
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
