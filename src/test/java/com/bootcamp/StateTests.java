package com.bootcamp;

import com.bootcamp.schema.TokenSchemaV1;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.testing.core.TestIdentity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StateTests {
    private final Party alice = new TestIdentity(new CordaX500Name("Alice", "", "GB")).getParty();
    private final Party bob = new TestIdentity(new CordaX500Name("Bob", "", "GB")).getParty();

    @Test
    public void tokenStateHasIssuerOwnerAndAmountParamsOfCorrectTypeInConstructor() {
        new TokenState(alice, bob, 1, new UniqueIdentifier(), new ArrayList<>());
    }

    @Test
    public void tokenStateHasGettersForIssuerOwnerAndAmount() {
        TokenState tokenState = new TokenState(alice, bob, 1, new UniqueIdentifier(), new ArrayList<>());
        assertEquals(alice, tokenState.getIssuer());
        assertEquals(bob, tokenState.getOwner());
        assertEquals(1, tokenState.getAmount());
    }

    @Test
    public void tokenStateImplementsContractState() {
        assert(new TokenState(alice, bob, 1, new UniqueIdentifier(), new ArrayList<>()) instanceof ContractState);
    }

    @Test
    public void tokenStateHasTwoParticipantsTheIssuerAndTheOwner() {
        TokenState tokenState = new TokenState(alice, bob, 1, new UniqueIdentifier(), new ArrayList<>());
        assertEquals(2, tokenState.getParticipants().size());
        assert(tokenState.getParticipants().contains(alice));
        assert(tokenState.getParticipants().contains(bob));
    }
}