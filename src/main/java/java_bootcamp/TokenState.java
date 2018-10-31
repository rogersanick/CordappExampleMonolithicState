package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/java/examples/ArtState.java for an example. */
public class TokenState implements ContractState {

    private Party owner;
    private Party issuer;
    private int amount;

    public Party getOwner() {
        return owner;
    }

    public Party getIssuer() {
        return issuer;
    }

    public int getAmount() {
        return amount;
    }

    public TokenState (Party issuer, Party owner, int amount) {
        this.owner = owner;
        this.issuer = issuer;
        this.amount = amount;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(issuer, owner);
    }

}