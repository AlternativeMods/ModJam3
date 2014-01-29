package jkmau5.modjam.radiomod.world.gen.structure;

import jkmau5.modjam.radiomod.RMLogger;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

/**
 * Created by mattashii on 1/13/14.
 *
 * the studio implemented in the villages
 */
public class ComponentVillageStudio extends ComponentVillage {
    private int averageGroundLevel = -1;

    public ComponentVillageStudio(){

    }

    public ComponentVillageStudio(ComponentVillageStartPiece startPiece, int par1, Random random, StructureBoundingBox structureBoundingBox, int par5){
        super(startPiece, par1);
        this.coordBaseMode = par5;
        this.boundingBox = structureBoundingBox;
    }

    public static ComponentVillageStudio buildComponent(ComponentVillageStartPiece villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5){
        StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 10, 17, 11, p4);
        return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(pieces, structureBoundingBox) == null ? new ComponentVillageStudio(villagePiece, p5, random, structureBoundingBox, p4) : null;
    }

    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox){
        if (this.averageGroundLevel < 0){
            this.averageGroundLevel = this.getAverageGroundLevel(world, structureBoundingBox);

            if (this.averageGroundLevel < 0){
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 16, 0);

        }

        // data values

        // -stairs
        int DE = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
        int DW = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
        int DS = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 0);
        int DN = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 1);

        int UE = DE + 4;
        int UW = DW + 4;
        int US = DS + 4;
        int UN = DN + 4;

        // -wood beams
        int NB = this.setWooddata2(DE);
        int WB = this.setWooddata1(DE);

        // -ladders
        int l3 = this.getMetadataWithOffset(Block.ladder.blockID, 5);
        int l2 = this.getMetadataWithOffset(Block.ladder.blockID, 4);

        // some surfaces

        this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 9, 0, 8, Block.cobblestone.blockID, Block.cobblestone.blockID, false); //base
        this.fillWithBlocks(world, structureBoundingBox, 4, 12, 2, 5, 12, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false); //F3
        this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 8, 4, 7, Block.planks.blockID, Block.planks.blockID, false); //F1

        // fences around the balconies

        this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 9, 5, 0, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 5, 8, 9, 5, 8, Block.fence.blockID, Block.fence.blockID, false);

        // windows

        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 7, 8, 3, 7, Block.stairsWoodOak.blockID, UE, Block.stairsWoodOak.blockID, UE, false);
        this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 8, 2, 7, Block.thinGlass.blockID, Block.thinGlass.blockID, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 9, 1, 2, 9, 5, 6, Block.stairsWoodOak.blockID, UN, Block.stairsWoodOak.blockID, UN, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 2, 2, 9, 2, 6, Block.thinGlass.blockID, Block.thinGlass.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 6, 2, 9, 6, 6, Block.thinGlass.blockID, Block.thinGlass.blockID, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 1, 1, 8, 3, 1, Block.stairsWoodOak.blockID, UW, Block.stairsWoodOak.blockID, UW, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 2, 1, 8, 2, 1, Block.thinGlass.blockID, Block.thinGlass.blockID, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 3, 1, 2, 3, 1, Block.stairsWoodOak.blockID, UW, Block.stairsWoodOak.blockID, UW, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 2, 0, 5, 6, Block.stairsWoodOak.blockID, US, Block.stairsWoodOak.blockID, US, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 2, 2, 0, 2, 6, Block.thinGlass.blockID, Block.thinGlass.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 6, 2, 0, 6, 6, Block.thinGlass.blockID, Block.thinGlass.blockID, false);


        // front doors
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 1, 1, 1, 1, Block.doorWood.blockID, this.remapDirection(DS), Block.doorWood.blockID, this.remapDirection(this.remapDirection(DS)), false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 1, 1, 2, 1, 1, Block.doorWood.blockID, this.remapDirection(DS), Block.doorWood.blockID, this.remapDirection(this.remapDirection(DE)), false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 1, 1, 2, 1, Block.doorWood.blockID, 8, Block.doorWood.blockID, 8, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 2, 1, 2, 2, 1, Block.doorWood.blockID, 9, Block.doorWood.blockID, 9, false);

        // roofings

        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 7, 0, 8, 7, 0, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 8, 1, 8, 8, 1, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 8, 7, 8, 8, 7, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 7, 8, 8, 7, 8, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 7, 0, 0, 7, 8, Block.stairsWoodOak.blockID, DS, Block.stairsWoodOak.blockID, DS, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 8, 1, 1, 8, 7, Block.stairsWoodOak.blockID, DS, Block.stairsWoodOak.blockID, DS, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 9, 7, 0, 9, 7, 8, Block.stairsWoodOak.blockID, DN, Block.stairsWoodOak.blockID, DN, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 8, 8, 1, 8, 8, 7, Block.stairsWoodOak.blockID, DN, Block.stairsWoodOak.blockID, DN, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 9, 2, 2, 9, 2, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 9, 2, 7, 9, 2, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 9, 6, 2, 9, 6, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 9, 6, 7, 9, 6, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 9, 3, 2, 9, 5, Block.stairsWoodOak.blockID, DS, Block.stairsWoodOak.blockID, DS, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 9, 3, 7, 9, 5, Block.stairsWoodOak.blockID, DN, Block.stairsWoodOak.blockID, DN, false);

        this.fillWithBlocks(world, structureBoundingBox, 4, 8, 1, 5, 8, 1, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 8, 7, 5, 8, 7, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 9, 1, 5, 9, 1, Block.thinGlass.blockID, Block.thinGlass.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 9, 7, 5, 9, 7, Block.thinGlass.blockID, Block.thinGlass.blockID, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 10, 1, 5, 10, 1, Block.stairsWoodOak.blockID, UW, Block.stairsWoodOak.blockID, UW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 10, 7, 5, 10, 7, Block.stairsWoodOak.blockID, UE, Block.stairsWoodOak.blockID, UE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 11, 1, 5, 11, 1, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 11, 7, 5, 11, 7, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 12, 1, 5, 12, 1, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 12, 7, 5, 12, 7, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 13, 1, 5, 13, 1, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 4, 13, 7, 5, 13, 7, Block.fence.blockID, Block.fence.blockID, false);

        this.fillWithBlocks(world, structureBoundingBox, 3, 10, 2, 3, 10, 6, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 10, 2, 6, 10, 6, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 12, 2, 3, 12, 6, Block.stairsWoodOak.blockID, DS, Block.stairsWoodOak.blockID, DS, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 6, 12, 2, 6, 12, 6, Block.stairsWoodOak.blockID, DN, Block.stairsWoodOak.blockID, DN, false);
        this.fillWithBlocks(world, structureBoundingBox, 3, 13, 2, 3, 13, 6, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 13, 2, 6, 13, 6, Block.fence.blockID, Block.fence.blockID, false);

        // inside

        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 8, 2, 2, 8, 6, Block.stairsWoodOak.blockID, UN, Block.stairsWoodOak.blockID, UN, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 9, 2, 3, 9, 6, Block.stairsWoodOak.blockID, UN, Block.stairsWoodOak.blockID, UN, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 6, 9, 2, 6, 9, 6, Block.stairsWoodOak.blockID, US, Block.stairsWoodOak.blockID, US, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 8, 2, 7, 8, 6, Block.stairsWoodOak.blockID, US, Block.stairsWoodOak.blockID, US, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 8, 2, 7, 8, 6, Block.woodSingleSlab.blockID, 8, Block.woodSingleSlab.blockID, 8, false);

        // stairs F0-F1
        this.fillWithBlocks(world, structureBoundingBox, 8, 4, 2, 8, 4, 4, 0, 0, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 8, 4, 5, 8, 4, 5, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 8, 3, 4, 8, 3, 4, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 8, 2, 3, 8, 2, 3, Block.stairsWoodOak.blockID, DW, Block.stairsWoodOak.blockID, DW, false);
        this.fillWithBlocks(world, structureBoundingBox, 8, 1, 2, 8, 1, 2, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 1, 2, 7, 1, 2, Block.stairsWoodOak.blockID, DS, Block.stairsWoodOak.blockID, DS, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 4, 2, 7, 4, 2, Block.stairsWoodOak.blockID, UN, Block.stairsWoodOak.blockID, UN, false);

        // ladder F1-F2
        this.fillWithBlocks(world, structureBoundingBox, 3, 5, 2, 3, 8, 2, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 5, 2, 4, 8, 2, Block.ladder.blockID, l3, Block.ladder.blockID, l3, false);

        // ladder F2-F3
        this.fillWithBlocks(world, structureBoundingBox, 6, 9, 6, 6, 9, 6, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 5, 9, 6, 5, 12, 6, Block.ladder.blockID, l2, Block.ladder.blockID, l2, false);

        // horizontal wooden beams
        // -long horizontal front/back

        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 0, 9, 4, 0, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 8, 9, 4, 8, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 1, 9, 4, 1, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 7, 9, 4, 7, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 7, 1, 9, 7, 1, Block.wood.blockID, NB, Block.wood.blockID, NB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 7, 7, 9, 7, 7, Block.wood.blockID, NB, Block.wood.blockID, NB, false);

        // -smaller horizontal in&out

        this.fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 2, 0, 4, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 4, 2, 3, 4, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 6, 4, 2, 6, 4, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 9, 4, 2, 9, 4, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 11, 2, 3, 11, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 6, 11, 2, 6, 11, 6, Block.wood.blockID, WB, Block.wood.blockID, WB, false);


        // pillars

        this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 3, 0, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 1, 8, 0, 3, 8, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 3, 3, 0, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 3, 1, 8, 3, 3, 8, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 1, 0, 6, 3, 0, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 1, 8, 6, 3, 8, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 1, 8, 9, 3, 8, Block.cobblestoneWall.blockID, Block.cobblestoneWall.blockID, false);

        // small wooden pillars

        this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 8, 1, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 1, 4, 0, 8, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 0, 8, 7, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 1, 1, 9, 8, 1, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 1, 4, 9, 8, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 9, 1, 7, 9, 8, 7, Block.wood.blockID, Block.wood.blockID, false);

        // large wooden pillars

        this.fillWithBlocks(world, structureBoundingBox, 3, 1, 1, 3, 13, 1, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 1, 1, 6, 13, 1, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 3, 1, 7, 3, 13, 7, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(world, structureBoundingBox, 6, 1, 7, 6, 13, 7, Block.wood.blockID, Block.wood.blockID, false);

        // Step-ups

        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 0, 2, 0, 0, Block.stairsCobblestone.blockID, DW, Block.stairsCobblestone.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 0, 0, 5, 0, 0, Block.stairsCobblestone.blockID, DW, Block.stairsCobblestone.blockID, DW, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 0, 0, 8, 0, 0, Block.stairsCobblestone.blockID, DW, Block.stairsCobblestone.blockID, DW, false);

        this.fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 8, 2, 0, 8, Block.stairsCobblestone.blockID, DE, Block.stairsCobblestone.blockID, DE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 0, 8, 5, 0, 8, Block.stairsCobblestone.blockID, DE, Block.stairsCobblestone.blockID, DE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 7, 0, 8, 8, 0, 8, Block.stairsCobblestone.blockID, DE, Block.stairsCobblestone.blockID, DE, false);

        // interior
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 4, 3, 1, 4, Block.stairsWoodOak.blockID, DE, Block.stairsWoodOak.blockID, DE, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 2, 1, 6, 2, 1, 6, RadioMod.instance.blockPlaylist.blockID, RemapDirection(DW), RadioMod.instance.blockPlaylist.blockID, RemapDirection(DW), false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 4, 1, 6, 4, 1, 6, RadioMod.instance.blockPlaylist.blockID, RemapDirection(DW), RadioMod.instance.blockPlaylist.blockID, RemapDirection(DW), false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 6, 3, 1, 6, RadioMod.instance.blockBroadcaster.blockID, RemapDirection(DW), RadioMod.instance.blockBroadcaster.blockID, RemapDirection(DW), false);

        // antennas
        this.fillWithMetadataBlocks(world, structureBoundingBox, 5, 13, 3, 5, 13, 3, RadioMod.instance.blockAntenna.blockID, 0, RadioMod.instance.blockAntenna.blockID, 0, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 5, 13, 5, 5, 13, 5, RadioMod.instance.blockAntenna.blockID, 0, RadioMod.instance.blockAntenna.blockID, 0, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 5, 14, 3, 5, 14, 3, RadioMod.instance.blockAntenna.blockID, 1, RadioMod.instance.blockAntenna.blockID, 1, false);
        this.fillWithMetadataBlocks(world, structureBoundingBox, 5, 14, 5, 5, 14, 5, RadioMod.instance.blockAntenna.blockID, 1, RadioMod.instance.blockAntenna.blockID, 1, false);

        // do the remaining stuff

        for (int x = 0; x < 10; ++x){
            for (int y = 0; y < 9; ++y){
                this.clearCurrentPositionBlocksUpwards(world, x, 15, y, structureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, x, -1, y, structureBoundingBox);
            }
        }

        int x1 = this.getXWithOffset(5, 3);
        int x2 = this.getXWithOffset(5, 5);
        int y = this.getYWithOffset(13);
        int z1 = this.getZWithOffset(5, 3);
        int z2 = this.getZWithOffset(5, 5);


        TileEntityAntenna tileEntityAntenna1 = (TileEntityAntenna) world.getBlockTileEntity(x1, y, z1);
        TileEntityAntenna tileEntityAntenna2 = (TileEntityAntenna) world.getBlockTileEntity(x2, y, z2);

        if (tileEntityAntenna1 != null && tileEntityAntenna2 != null)
        {
            tileEntityAntenna1.yaw = RemapDirection(DW) * 90 % 360 - 90;
            tileEntityAntenna2.yaw = RemapDirection(DE) * 90 % 360 - 90;
        }

        return true;
    }

    int setWooddata1(int d){
        int r = 0;
        switch (d){
            case 0:
            case 1: r = 4; break;
            case 2:
            case 3: r = 8; break;
        }
        return r;
    }

    int setWooddata2(int d){
        int r = 0;
        switch (d){
            case 0:
            case 1: r = 8; break;
            case 2:
            case 3: r = 4; break;
        }
        return r;
    }

    int RemapDirection(int direction){
        switch (direction){
            case 3: return 0;
            case 0: return 1;
            case 2: return 2;
            case 1: return 3;
        }
        return 0;
    }

    int remapDirection(int direction){
        switch (direction){
            case 0: return 3;
            case 1: return 2;
            case 2: return 0;
            case 3: return 1;
        }
        return -1;
    }
}