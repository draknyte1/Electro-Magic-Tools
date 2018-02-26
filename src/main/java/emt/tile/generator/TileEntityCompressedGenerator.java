package emt.tile.generator;

import emt.util.EMTConfigHandler;
import ic2.api.energy.prefab.BasicSource;
import thaumcraft.api.aspects.Aspect;

public abstract class TileEntityCompressedGenerator
  extends TileEntityBaseGenerator
{
  public TileEntityCompressedGenerator(Aspect aspect)
  {
    super(aspect);
    this.energySource = new BasicSource(this, 1000000.0D, 3);
    this.energySource.setCapacity(EMTConfigHandler.EssentiaGeneratorStorage * 10.0D);
    this.maxfuel *= 10;
  }
}
