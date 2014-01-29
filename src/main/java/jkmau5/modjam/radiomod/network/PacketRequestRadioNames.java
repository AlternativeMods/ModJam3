package jkmau5.modjam.radiomod.network;

/*public class PacketRequestRadioNames extends PacketBase {

    public int dimensionId;
    public boolean isMediaPlayer;
    public TileEntityRadio tileEntity;
    public EntityPlayer player;

    public PacketRequestRadioNames(){
    }

    public PacketRequestRadioNames(int dimensionId, EntityPlayer player){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = true;
        this.player = player;
    }

    public PacketRequestRadioNames(int dimensionId, TileEntityRadio tileEntity){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = false;
        this.tileEntity = tileEntity;
    }

    @Override
    public void encode(ByteBuf buffer){
        if(FMLCommonHandler.instance().getEffectiveSide().isServer()){
            List<String> radioList;
            if(this.isMediaPlayer){
                radioList = RadioMod.radioNetworkHandler.getAvailableRadioNames(this.player.worldObj, (int) this.player.posX, (int) this.player.posY, (int) this.player.posZ);
            }else{
                radioList = RadioMod.radioNetworkHandler.getAvailableRadioNames(this.tileEntity.func_145831_w(), this.tileEntity.field_145851_c, this.tileEntity.field_145848_d, this.tileEntity.field_145849_e);
            }
            if(!radioList.isEmpty()){
                buffer.writeInt(radioList.size());
                for(String radio : radioList){
                    ByteBufUtils.writeUTF8String(buffer, radio);
                }
            }else{
                buffer.writeInt(0);
            }
        }else{
            buffer.writeBoolean(this.isMediaPlayer);
            if(!this.isMediaPlayer){
                buffer.writeInt(this.tileEntity.func_145831_w().provider.dimensionId);
                buffer.writeInt(this.tileEntity.field_145851_c);
                buffer.writeInt(this.tileEntity.field_145848_d);
                buffer.writeInt(this.tileEntity.field_145849_e);
            }else{
                buffer.writeInt(this.player.worldObj.provider.dimensionId);
            }
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()){
            int listSize = input.readInt();
            List<String> radios = Lists.newArrayList();
            for(int i = 0; i < listSize; i++){
                radios.add(input.readUTF());
            }
            GuiMediaPlayer.updateRadioStations(radios);
        }else{
            this.isMediaPlayer = input.readBoolean();
            this.dimensionId = input.readInt();
            if(isMediaPlayer){
                PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId, this.player).getPacket(), (Player) this.player);
            }else{
                World world = DimensionManager.getWorld(this.dimensionId);
                if(world == null) return;
                int x = input.readInt();
                int y = input.readInt();
                int z = input.readInt();
                TileEntityRadio radio = (TileEntityRadio) world.getBlockTileEntity(x, y, z);
                if(radio == null) return;
                PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId, radio).getPacket(), (Player) this.player);
            }
        }
    }
}*/
