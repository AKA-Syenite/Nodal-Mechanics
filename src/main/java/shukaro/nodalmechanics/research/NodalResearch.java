package shukaro.nodalmechanics.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import shukaro.nodalmechanics.items.NodalItems;
import shukaro.nodalmechanics.recipe.NodalRecipes;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class NodalResearch
{
    public static ResearchItem researchNodeCatalyzation;

    public static void initResearch()
    {
        researchNodeCatalyzation = new ResearchItem("NODECATALYZATION", "BASICS", new AspectList().add(Aspect.AURA, 6).add(Aspect.VOID, 3), -5, 4, 3, new ItemStack(NodalItems.itemMatrix))
                                                    .setPages(new ResearchPage[]{
                                                    new ResearchPage(StatCollector.translateToLocal("nodalmechanics.nodecatalyzation.research")),
                                                    new ResearchPage(NodalRecipes.matrixRecipe),
                                                    new ResearchPage(NodalRecipes.variedAttuneRecipe),
                                                    new ResearchPage(NodalRecipes.sameAttuneRecipe),
                                                    new ResearchPage(NodalRecipes.nodeRecipe)})
                                                    .setParents("NODEJAR", "NODETAPPER2").setParentsHidden("JARLABEL").setSpecial();
        researchNodeCatalyzation.registerResearchItem();

        ThaumcraftApi.addWarpToResearch("NODECATALYZATION", 2);
        ThaumcraftApi.addWarpToItem(new ItemStack(NodalItems.itemMatrix), 1);
    }
}
