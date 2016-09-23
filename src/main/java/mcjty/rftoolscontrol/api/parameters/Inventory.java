package mcjty.rftoolscontrol.api.parameters;

import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class identifies an inventory on a network. It has an optional
 * node name. If that is not given then the processor itself is meant.
 * There is also a side adjacent to the node or processor and an
 * optional internal side. The internal side represents from which side
 * we are supposedly accessing the inventory.
 */
public class Inventory {
    @Nullable private final String nodeName;          // An inventory on a network
    @Nonnull private final EnumFacing side;      // The side at which the inventory can be found
    @Nullable private final EnumFacing intSide;   // The side at which we are accessing the inventory (can be null)

    public Inventory(@Nullable String name, @Nonnull EnumFacing side, @Nullable EnumFacing intSide) {
        this.nodeName = (name == null || name.isEmpty()) ? null : name;
        this.side = side;
        this.intSide = intSide;
    }

    @Nullable
    public String getNodeName() {
        return nodeName;
    }

    public boolean hasNodeName() {
        return nodeName != null && !nodeName.isEmpty();
    }

    public String serialize() {
        return "#" + (hasNodeName() ? nodeName : "-") + "#" + side.getName() + "#" + (intSide == null ? "-" : intSide.getName()) + "#";
    }

    public static Inventory deserialize(String s) {
        String[] splitted = StringUtils.split(s, '#');
        return new Inventory("-".equals(splitted[0]) ? null : splitted[0], EnumFacing.byName(splitted[1]),
                "-".equals(splitted[2]) ? null : EnumFacing.byName(splitted[2]));
    }

    @Nonnull
    public EnumFacing getSide() {
        return side;
    }

    @Nullable
    public EnumFacing getIntSide() {
        return intSide;
    }

    public String getStringRepresentation() {
        String s = StringUtils.left(getSide().getName().toUpperCase(), 1);
        if (getIntSide() == null) {
            s += "/*";
        } else {
            String is = StringUtils.left(getIntSide().getName().toUpperCase(), 1);
            s += "/" + is;
        }
        if (getNodeName() == null) {
            return s;
        } else {
            return StringUtils.left(getNodeName(), 6) + " " + s;
        }
    }
}