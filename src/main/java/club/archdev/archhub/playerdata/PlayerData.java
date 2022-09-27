package club.archdev.archhub.playerdata;

import club.archdev.archhub.playerdata.currentgame.PlayerCurrentGameData;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {

    private PlayerState playerState = PlayerState.SPAWN;

    private PlayerSettings playerSettings = new PlayerSettings();
    private PlayerCurrentGameData currentGameData = new PlayerCurrentGameData();

    private final UUID uniqueId;
    private boolean loaded;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.loaded = true;
    }
}
