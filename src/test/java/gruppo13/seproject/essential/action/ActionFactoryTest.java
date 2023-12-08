package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ActionFactoryTest {
    private Map.Entry<ActionType, List<String>> action;
    private List<String> params = new ArrayList<>();
    @Test
    void setUp() {
        params.add("title");
        params.add("header");
        params.add("message");

        action = Map.entry(ActionType.DIALOGBOX, params);
    }

    @Test
    void createAction() {
        Action action1 = new DialogBoxAction("title", "header", "message");
        Assert.assertEquals(ActionFactory.createAction(action).toString(), action1.toString());
    }

    @Test
    void createNullAction() {
        assertNull(ActionFactory.createAction(null));
    }

    @Test
    void createWrongParametersAction() {
        Map.Entry<ActionType, List<String>> action;
        List<String> params = new ArrayList<>();
        params.add("title");

        action = Map.entry(ActionType.DIALOGBOX, params);
        assertNull(ActionFactory.createAction(action));
    }

}