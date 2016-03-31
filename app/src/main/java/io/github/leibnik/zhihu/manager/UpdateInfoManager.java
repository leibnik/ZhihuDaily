package io.github.leibnik.zhihu.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Droidroid on 2016/3/31.
 */
public class UpdateInfoManager {
    public static List<OnUpdateInfoListener> listeners = new ArrayList<>();

    public interface OnUpdateInfoListener {
        void updateName();

        void updateAvatar();
    }

    public static void addOnUpdateInfoListener(OnUpdateInfoListener listener) {
        listeners.add(listener);
    }

    public static void updateInfo() {
        for (OnUpdateInfoListener listener : listeners) {
            listener.updateName();
            listener.updateAvatar();
        }
    }
}
