package com.bootcamp.schema;

import com.bootcamp.TokenState;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.serialization.CordaSerializable;

import javax.persistence.*;

/**
 * An IOUState schema.
 */

@CordaSerializable
    public class TokenChildSchemaV1 extends MappedSchema {

    public TokenChildSchemaV1() {
        super(TokenChildSchemaV1.class, 1, ImmutableList.of(PersistentChildToken.class));
    }

    @Entity
    @Table(name = "token_child_states")
    //@NamedQuery(name = "ParticipantLossEvent.findAll", query = "SELECT p FROM ParticipantLossEvent p")
    public static class PersistentChildToken extends PersistentState {
        @Column(name = "owner") private final String owner;
        @Column(name = "issuer") private final String issuer;
        @Column(name = "amount") private final int amount;
        @Column(name = "child proof") private final String childProof;
        @ManyToOne() private final TokenSchemaV1.PersistentToken persistentToken;

        public PersistentChildToken(String owner, String issuer, int amount) {
            this.owner = owner;
            this.issuer = issuer;
            this.amount = amount;
            this.persistentToken = null;
            this.childProof = "I am a child";
        }

        // Default constructor required by hibernate.
        public PersistentChildToken() {
            this.owner = "";
            this.issuer = "";
            this.amount = 0;
            this.persistentToken = null;
            this.childProof = "I am a child";
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

        public TokenSchemaV1.PersistentToken getTokenParent() {
            return persistentToken;
        }

    }

}
