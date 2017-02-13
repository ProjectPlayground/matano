package matano.apkode.net.matano.config;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FbStorage {
    private FirebaseStorage storage;

    private StorageReference refRoot;


    public FbStorage() {
        storage = FirebaseStorage.getInstance();
        refRoot = storage.getReference();
    }

    public StorageReference getRefRoot() {
        return refRoot;
    }

    // Photos
    public StorageReference getRefPhotos() {
        return refRoot.child(Utils.FIREBASE_STORAGE_PHOTOS);
    }

    // Events
    public StorageReference getRefPhotosEvents() {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_EVENTS);
    }

    // Event
    public StorageReference getRefPhotosEvent(String eventUid) {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_EVENTS).child(eventUid);
    }

    // Users
    public StorageReference getRefPhotosUsers() {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_USERS);
    }

    // User
    public StorageReference getRefPhotosUser(String userUid) {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_USERS).child(userUid);
    }

    // Profils
    public StorageReference getRefPhotosProfils() {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_PROFILS);
    }

    // Profil
    public StorageReference getRefPhotosProfil(String profilUid) {
        return getRefPhotos().child(Utils.FIREBASE_STORAGE_PHOTOS_PROFILS).child(profilUid);
    }

    // Videos
    public StorageReference getRefVideos() {
        return refRoot.child(Utils.FIREBASE_STORAGE_VIDEOS);
    }

}
