package shukaro.nodalmechanics.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import shukaro.nodalmechanics.items.NodalItems;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

public class NodalRecipes
{
    public static ShapedArcaneRecipe matrixRecipe;
    public static ShapedArcaneRecipe variedAttuneRecipe;
    public static ShapedArcaneRecipe sameAttuneRecipe;
    public static InfusionRecipe variedNodeRecipe;
    public static InfusionRecipe sameNodeRecipe;

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

        NBTTagCompound variedAttuneTag = new NBTTagCompound();
        variedAttuneTag.setString("aspects", "aer,terra,ignis,aqua,ordo,perditio");
        ItemStack variedAttune = new ItemStack(NodalItems.itemMatrix);
        variedAttune.setTagCompound(variedAttuneTag);
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
        variedAttuneRecipe = new ShapedArcaneRecipe("NODECATALYZATION", variedAttune, new AspectList().add(Aspect.ORDER, 8), new Object[] {
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

        NBTTagCompound sameAttuneTag = new NBTTagCompound();
        sameAttuneTag.setString("aspects", "ignis,ignis,ignis,ignis,ignis,ignis,ignis,ignis");
        ItemStack sameAttune = new ItemStack(NodalItems.itemMatrix);
        sameAttune.setTagCompound(sameAttuneTag);
        ItemStack ignisPhial = ItemApi.getItem("itemEssence", 1);
        NBTTagCompound ignisTag = new NBTTagCompound();
        AspectList ignisList = new AspectList().add(Aspect.FIRE, 8);
        ignisList.writeToNBT(ignisTag);
        ignisPhial.setTagCompound(ignisTag);
        sameAttuneRecipe = new ShapedArcaneRecipe("NODECATALYZATION", sameAttune, new AspectList().add(Aspect.ORDER, 8), new Object[] {
                "AAA",
                "ABA",
                "AAA",
                Character.valueOf('A'), ignisPhial.copy(),
                Character.valueOf('B'), NodalItems.itemMatrix
        });

        NBTTagCompound nodeTag = new NBTTagCompound();
        AspectList nodeAspects = new AspectList().add(Aspect.AIR, 15).add(Aspect.EARTH, 15).add(Aspect.FIRE, 15).add(Aspect.WATER, 15).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15);
        nodeAspects.writeToNBT(nodeTag);
        nodeTag.setInteger("nodetype", 0);
        ItemStack node = ItemApi.getItem("itemJarNode", 0);
        node.setTagCompound(nodeTag);
        variedNodeRecipe = new InfusionRecipe("NODECATALYZATION", node, 10, new AspectList().add(Aspect.AIR, 150).add(Aspect.EARTH, 150).add(Aspect.FIRE, 150).add(Aspect.WATER, 150).add(Aspect.ORDER, 150).add(Aspect.ENTROPY, 150), variedAttune, new ItemStack[] { ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14)});

        NBTTagCompound sameNodeTag = new NBTTagCompound();
        AspectList sameNodeAspects = new AspectList().add(Aspect.AIR, 15 * 8);
        sameNodeAspects.writeToNBT(sameNodeTag);
        sameNodeTag.setInteger("nodetype", 0);
        ItemStack sameNode = ItemApi.getItem("itemJarNode", 0);
        sameNode.setTagCompound(sameNodeTag);
        sameNodeRecipe = new InfusionRecipe("NODECATALYZATION", sameNode, 10, new AspectList().add(Aspect.FIRE, 150 * 8), sameAttune, new ItemStack[] { ItemApi.getItem("itemResource", 14), ItemApi.getItem("itemResource", 14)});
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
