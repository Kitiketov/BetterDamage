package net.horny_tomato.betterdamage.client;

import net.horny_tomato.betterdamage.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BetterDamageClient implements ClientModInitializer {
    public static String filePath;
    public static File file;

    @Override
    public void onInitializeClient() {
        filePath = System.getProperty("user.dir") + File.separator + "betterdamage.txt";
        file = new File(BetterDamageClient.filePath);
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.DAMAGE_MSG, (client, handler, buf, responseSender) -> {
            float amount = buf.readFloat();
            writeToFile(amount);
        });
    }

    public void writeToFile(float amount){

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("shock/" + Math.round(amount) + "/"
                    + Long.toString(System.currentTimeMillis())
                    .substring(0, Long.toString(System.currentTimeMillis()).length() - 3) + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something goes wrong!"+e);
        }
    }

}