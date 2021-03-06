package com.minecraftabnormals.environmental.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.ThiefHoodModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalAttributes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class ThiefHoodItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "ThiefHoodUses";

	public ThiefHoodItem(Properties properties) {
		super(EquipmentSlotType.HEAD, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return ThiefHoodModel.get(1.0F);
	}

	@Override
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity) {
		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(this.getEquipmentSlot()));
		UUID uuid = UUID.fromString("1D45B301-E65D-47A2-B63F-6EC5FCAC9316");

		int uses = Math.round(stack.getOrCreateTag().getFloat(NBT_TAG));
		double increase = 0.15D * getIncreaseForUses(uses);

		builder.put(EnvironmentalAttributes.STEALTH.get(), new AttributeModifier(uuid, "Stealth", increase, AttributeModifier.Operation.ADDITION));
		return slot == this.slot ? builder.build() : super.getAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void playerNameEvent(PlayerEvent.NameFormat event) {
		ItemStack stack = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD);
		if (stack.getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
			event.setDisplayname(stack.hasDisplayName() ? new StringTextComponent(stack.getDisplayName().getString()).mergeStyle(TextFormatting.ITALIC) : new StringTextComponent("???"));
		}
	}

	@SubscribeEvent
	public static void hoodEquippedEvent(LivingEquipmentChangeEvent event) {
		if (event.getTo().getItem() == EnvironmentalItems.THIEF_HOOD.get() || event.getFrom().getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				((PlayerEntity) event.getEntityLiving()).refreshDisplayName();
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();

		if (event.getSource().getTrueSource() instanceof LivingEntity && entity instanceof MonsterEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
			ItemStack stack = attacker.getItemStackFromSlot(EquipmentSlotType.HEAD);
			if (stack.getItem() instanceof ThiefHoodItem) {
				CompoundNBT tag = stack.getOrCreateTag();
				tag.putInt(NBT_TAG, tag.getInt(NBT_TAG) + 1);
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compound = stack.getOrCreateTag();
		int uses = compound.getInt(NBT_TAG);
		tooltip.add((new StringTextComponent(Integer.toString(uses) + " monsters slain")).mergeStyle(TextFormatting.GRAY));
		//TODO: Detection range attribute
	}

	public static int getIncreaseForUses(int uses) {
		int increase = 1;
		if (uses >= 10)
			increase += 1;
		if (uses >= 50)
			increase += 1;
		if (uses >= 100)
			increase += 1;
		if (uses >= 500)
			increase += 1;
		return increase;
	}
}
