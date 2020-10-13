import java.util.EventListener;

public abstract class ListenerAdapter implements EventListener {
    public void OnMemberJoined(Member member){}
    public void OnMemberLeft(Member member){}
}
