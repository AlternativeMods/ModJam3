package jkmau5.modjam.radiomod.network;

/*public class PacketSelectRadio extends PacketBase {

    public String selectedName;
    public boolean isMediaPlayer;
    public TileEntityRadio tileEntity;

    public PacketSelectRadio(){}
    public PacketSelectRadio(String selected){
        this.selectedName = selected;
        this.isMediaPlayer = true;
    }

    public PacketSelectRadio(String selected, TileEntityRadio radio){
        this.selectedName = selected;
        this.tileEntity = radio;
        this.isMediaPlayer = false;
    }

    @Override
    public void encode(ByteBuf buffer){
        buffer.writeBoolean(this.isMediaPlayer);
        ByteBufUtils.writeUTF8String(buffer, this.selectedName);
        if(!this.isMediaPlayer){
            buffer.writeInt(this.tileEntity.func_145831_w().provider.dimensionId);
            buffer.writeInt(this.tileEntity.field_145851_c);
            buffer.writeInt(this.tileEntity.field_145848_d);
            buffer.writeInt(this.tileEntity.field_145849_e);
        }
    }

    @Override
    public void decode(ByteBuf buffer){
        this.isMediaPlayer = buffer.readBoolean();
        this.selectedName = ByteBufUtils.readUTF8String(buffer);
        if(!this.isMediaPlayer){
            World world = DimensionManager.getWorld(buffer.readInt());
            TileEntity tile = world.func_147438_o(buffer.readInt(), buffer.readInt(), buffer.readInt());
            if(tile == null || !(tile instanceof TileEntityRadio)) return;
            this.tileEntity = (TileEntityRadio) tile;
            this.tileEntity.setConnectedBroadcastStation(this.selectedName);
        }else{
            ItemStack stack = this.player.getCurrentEquippedItem();
            if(stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = stack.getTagCompound();
            tag.setString("station", this.selectedName);
        }
    }
}*/
