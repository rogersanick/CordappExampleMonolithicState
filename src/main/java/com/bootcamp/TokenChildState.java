package com.bootcamp;

import com.bootcamp.schema.TokenChildSchemaV1;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/java/examples/ArtState.java for an example. */
public class TokenChildState implements LinearState, QueryableState {

    private final Party owner;
    private final Party issuer;
    private final int amount;
    private final UniqueIdentifier linearId;

    public TokenChildState(Party issuer, Party owner, int amount, UniqueIdentifier linearId) {
        this.owner = owner;
        this.issuer = issuer;
        this.amount = amount;
        this.linearId = linearId;
    }

    public Party getOwner() {
        return owner;
    }

    public Party getIssuer() {
        return issuer;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @Override
    public TokenChildSchemaV1.PersistentChildToken generateMappedObject(MappedSchema schema) {
        if (schema instanceof TokenChildSchemaV1) {
            return new TokenChildSchemaV1.PersistentChildToken(
                    this.getOwner().getName().toString(),
                    this.getIssuer().getName().toString(),
                    this.getAmount()

            );
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @Override
    public List<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new TokenChildSchemaV1());
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(issuer, owner);
    }

}