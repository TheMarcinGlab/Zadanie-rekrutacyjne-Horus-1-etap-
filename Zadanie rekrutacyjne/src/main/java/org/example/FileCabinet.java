package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                for (Folder podFolder : ((MultiFolder) folder).getFolders()) {
                    Optional<Folder> znalezionyFolder = new FileCabinet(Arrays.asList(podFolder)).findFolderByName(name);
                    if (znalezionyFolder.isPresent()) {
                        return znalezionyFolder;
                    }
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> wynik = new ArrayList<>();

        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                wynik.add(folder);
            }
            if (folder instanceof MultiFolder) {
                wynik.addAll(new FileCabinet(((MultiFolder) folder).getFolders()).findFoldersBySize(size));
            }

        }
        return wynik;
    }

    @Override
    public int count() {

        return 0;
    }

}
