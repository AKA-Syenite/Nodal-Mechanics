package shukaro.nodalmechanics.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import shukaro.nodalmechanics.NodalMechanics;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class NodalRecipes
{
    public static ShapedArcaneRecipe matrixRecipe;
    public static ShapedArcaneRecipe attuneRecipe;
    public static InfusionRecipe nodeRecipe;

    public NodalRecipes()
    {
        matrixRecipe = new ShapedArcaneRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), new Object[] {
                "XYX",
                "YZY",
                "XYX",
                Character.valueOf('X'), ItemApi.getItem("itemResource", 14),
                Character.valueOf('Y'), ItemApi.getBlock("blockMagicalLog", 0),
                Character.valueOf('Z'), ItemApi.getBlock("blockJar", 0)
        });

        NBTTagCompound attuneTag = new NBTTagCompound();
        attuneTag.setString("aspects", "aer,terra,ignis,aqua,ordo,perditio");
        ItemStack attuned = new ItemStack(NodalItems.itemMatrix);
        attuned.setTagCompound(attuneTag);
        ItemStack[] phials = new ItemStack[6];
        Aspect[] primals = new Aspect[] { Aspect.AIR, Aspect.EARTH, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY };
        for (int i=0; i<phials.length; i++)
        {
            phials[i] = ItemApi.getItem("itemEssence", 1);
            NBTTagCompound tag = new NBTTagCompound();
            AspectList list = new AspectList().add(primals[i], 8);
            list.writeToNBT(tag);
            phials[i].setTagCompound(tag);
        }
        attuneRecipe = new ShapedArcaneRecipe("NODECATALYZATION", attuned, new AspectList().add(Aspect.ORDER, 8), new Object[] {
                "ABC",
                " X ",
                "DEF",
                Character.valueOf('A'), phials[0],
                Character.valueOf('B'), phials[1],
                Character.valueOf('C'), phials[2],
                Character.valueOf('D'), phials[3],
                Character.valueOf('E'), phials[4],
                Character.valueOf('F'), phials[5],
                Character.valueOf('X'), NodalItems.itemMatrix
        });

        NBTTagCompound nodeTag = new NBTTagCompound();
        AspectList nodeAspects = new AspectList().add(Aspect.AIR, 15).add(Aspect.EARTH, 15).add(Aspect.FIRE, 15).add(Aspect.WATER, 15).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15);
        nodeAspects.writeToNBT(nodeTag);
        nodeTag.setInteger("nodetype", 0);
        ItemStack node = ItemApi.getItem("itemJarNode", 0);
        node.setTagCompound(nodeTag);
        nodeRecipe = new InfusionRecipe("NODECATALYZATION", node, 6 * 3, new AspectList().add(Aspect.AIR, 150).add(Aspect.EARTH, 150).add(Aspect.FIRE, 150).add(Aspect.WATER, 150).add(Aspect.ORDER, 150).add(Aspect.ENTROPY, 150), attuned, new ItemStack[] { ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14)});
    }

    public void initRecipes()
    {
        ThaumcraftApi.addArcaneCraftingRecipe("NODECATALYZATION", new ItemStack(NodalItems.itemMatrix), new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20), new Object[]{
                "XYX",
                "YZY",
                "XYX",
                Character.valueOf('X'), ItemApi.getItem("itemResource", 14),
                Character.valueOf('Y'), ItemApi.getBlock("blockMagicalLog", 0),
                Character.valueOf('Z'), ItemApi.getBlock("blockJar", 0)
        });

        RecipeAttune ra = new RecipeAttune();
        ThaumcraftApi.getCraftingRecipes().add(ra);
        RecipeNode rn = new RecipeNode();
        ThaumcraftApi.getCraftingRecipes().add(rn);
    }
}
