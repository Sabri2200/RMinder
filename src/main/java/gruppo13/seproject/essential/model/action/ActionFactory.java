package gruppo13.seproject.essential.model.action;

public class ActionFactory {
    public ActionFactory() {}

    public Action createAudioAction(String filePath) {
        return new AudioAction(filePath);
    }

    public Action createMessageAction(String title, String message) {
        return new MessageAction(title, message);
    }

    /*public Action createAction(ActionType type, String[] params) {
        return switch (type) {
            case DIALOGBOX -> new MessageAction(params[0], params[1]);
            case MP3PLAYER -> new AudioAction(params[0]);
            default -> throw new IllegalArgumentException("This action type is not supported");
        };
    }*/
}
