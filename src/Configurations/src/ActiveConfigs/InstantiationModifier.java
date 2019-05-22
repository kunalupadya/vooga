package ActiveConfigs;

import Configs.Configurable;
import Configs.Updatable;

public interface InstantiationModifier {
    void apply(Configurable configurable);
}
