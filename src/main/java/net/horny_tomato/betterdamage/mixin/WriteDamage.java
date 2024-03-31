package net.horny_tomato.betterdamage.mixin;

import net.horny_tomato.betterdamage.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(PlayerEntity.class)
public class WriteDamage {
	@Inject(at = @At("RETURN"), method = "applyDamage")
	private void updateHealth(DamageSource source, float amount, CallbackInfo ci) throws IOException {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeFloat(amount);
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
		ServerPlayNetworking.send(player, ModMessages.DAMAGE_MSG, buf);
	}
}