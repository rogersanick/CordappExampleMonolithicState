package com.bootcamp.schema;

import com.bootcamp.TokenChildState;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.serialization.CordaSerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

/**
 * An IOUState schema.
 */

@CordaSerializable
public class TokenSchemaV1 extends MappedSchema {

    public TokenSchemaV1() {
        super(TokenSchema.class, 1, ImmutableList.of(PersistentToken.class, TokenChildSchemaV1.PersistentChildToken.class));
    }

    @Entity
    @Table(name = "token_states")
    public static class PersistentToken extends PersistentState {
        @Column(name = "owner") private final String owner;
        @Column(name = "issuer") private final String issuer;
        @Column(name = "amount") private final int amount;
        @Column(name = "linear_id") private final UUID linearId;
        @OneToMany(mappedBy = "persistentToken") private final List<TokenChildSchemaV1.PersistentChildToken> childTokens;
        //get() = field

        public PersistentToken(String owner, String issuer, int amount, UUID linearId, List<TokenChildSchemaV1.PersistentChildToken> childTokens) {
            this.owner = owner;
            this.issuer = issuer;
            this.amount = amount;
            this.linearId = linearId;
            this.childTokens = childTokens;
        }

        // Default constructor required by hibernate.
        public PersistentToken() {
            this.owner = "";
            this.issuer = "";
            this.amount = 0;
            this.linearId = UUID.randomUUID();
            this.childTokens = null;
        }

        public String getOwner() {
            return owner;
        }

        public String getIssuer() {
            return issuer;
        }

        public int getAmount() {
            return amount;
        }

        public UUID getLinearId() {
            return linearId;
        }

        public List<TokenChildSchemaV1.PersistentChildToken> getChildTokens() { return childTokens; }
    }
}
