package jkmau5.modjam.radiomod.world.gen.structure;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

/**
 * Created by mattashii on 1/14/14.
 *
 * the handler for the Village Studio
 */
public class VillageStudioHandler implements VillagerRegistry.IVillageCreationHandler {

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i){
        return null;//new StructureVillagePieces.PieceWeight(ComponentVillageStudio.class, 30, i + random.nextInt(4));
    }

    @Override
    public Class<?> getComponentClass(){
        return null;//ComponentVillageStudio.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5){
        return null;//ComponentVillageStudio.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}
