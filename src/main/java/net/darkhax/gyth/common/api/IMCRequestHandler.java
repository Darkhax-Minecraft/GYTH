package net.darkhax.gyth.common.api;

import net.darkhax.gyth.utils.Constants;
import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.nbt.NBTTagCompound;

public class IMCRequestHandler {

    public static void acceptIncomingMessage(NBTTagCompound tag) {

    }

    public static void handleNewUpgradeRequest(NBTTagCompound tag, String sender) {

        // TODO add config option for logging.
        if (true)
            Constants.LOG.info("Upgrade request from " + sender + " has been recieved and will now be processed.");

        if (tag.hasKey("EnumName") && tag.hasKey("UpgradeName") && tag.hasKey("TierValue") && tag.hasKey("TierCapacity"))
            EnumTankData.addEnumTankData(tag.getString("EnumName"), tag.getString("UpgradeName"), tag.getInteger("TierValue"), tag.getInteger("TierCapacity"));

        // TODO add config option for logging.
        else if (true)
            Constants.LOG.info("Upgrade request from " + sender + " has failed. One or more nbt tags is missing.");

    }
}