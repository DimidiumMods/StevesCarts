package vswe.stevescarts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.screen.slot.Slot;

@Mixin(Slot.class)
public interface SlotAccessor {
	@Mutable
	@Accessor("x")
	void setX(int x);

	@Mutable
	@Accessor("y")
	void setY(int y);
}
