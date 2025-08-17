package me.darragh.eamfhc.modlist;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.darragh.eamfhc.EntryPoint;
import net.minecraftforge.fml.*;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Patches {@link net.minecraftforge.fml.ModList} to hide the mod from the mod list.
 * This is done rather than using mixins as for whatever reason, the mixin does not work.
 *
 * @author darraghd493
 */
@Slf4j
public class ModListPatcher {
    private static boolean patched = false;

    public static void initPatch() {
        if (patched) return;
        patched = true;
        Thread patcherThread = new ModListPatcher$Thread();
        patcherThread.setDaemon(true);
        patcherThread.start();
    }

    //region Thread
    private static class ModListPatcher$Thread extends Thread {
        public ModListPatcher$Thread() {
            super("ModList-Patcher");
        }

        @SuppressWarnings("unchecked")
        @SneakyThrows
        @Override
        public void run() {
            log.info("ModList-PatcherThread starting");

            // Fetch the ModList class from the current thread's context class loader
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> modListClass = loader.loadClass(ModList.class.getName());
            log.info("Got ModList class: {}", modListClass.getName());

            // Fetch the ModList instance
            ModList modList = ModList.get();

            // Fetch fields from the ModList class
            Field modFilesField = modListClass.getDeclaredField("modFiles"),
                    sortedListField = modListClass.getDeclaredField("sortedList");

            modFilesField.setAccessible(true);
            sortedListField.setAccessible(true);

            List<IModFileInfo> modFiles = (List<IModFileInfo>) modFilesField.get(modList);
            List<IModInfo> sortedList = (List<IModInfo>) sortedListField.get(modList);

            modFiles.removeIf(modFileInfo ->
                    modFileInfo.getMods().stream()
                            .anyMatch(modInfo -> modInfo.getModId().equals(EntryPoint.MOD_ID))
            );
            sortedList.removeIf(modInfo ->
                    modInfo.getModId().equals(EntryPoint.MOD_ID)
            );
        }
    }

    //endregion
}
