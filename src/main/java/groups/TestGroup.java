package groups;

import java.util.List;

public interface TestGroup {

    List<Class<?>> getTestClasses();

    default String getSuiteName() {
        return this.getClass().getSimpleName() + " Suite";
    }
}