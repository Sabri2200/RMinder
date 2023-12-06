package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActionFactoryTest {
    private Map.Entry<ActionType, List<String>> action;

    @BeforeEach
    void setUp() {
        List<String> params = new ArrayList<>();
        params.add("title");
        params.add("header");
        params.add("message");

        action = Map.entry(ActionType.DIALOGBOX, params);
    }

    @Test
    void createAction() {
        Action action1 = new DialogBoxAction("title", "header", "message");

        assertEquals(ActionFactory.createAction(action).toString(), action1.toString());
    }

    @Test
    void createNullAction() {
        assertNull(ActionFactory.createAction(null));
    }

    @Test
    void createWrongParametersAction() {
        List<String> params = new ArrayList<>();
        params.add("title");

        action = Map.entry(ActionType.DIALOGBOX, params);
        assertNull(ActionFactory.createAction(action));
    }

}