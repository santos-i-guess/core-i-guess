package core.i.guess.lifetimes;

public interface PhasedComponent<T> extends Component
{

	void setPhase(T phase);
}
