//package java_bootcamp;
//
//import co.paralleluniverse.fibers.Suspendable;
//import com.google.common.collect.ImmutableList;
//import com.sun.javaws.progress.Progress;
//import net.corda.core.contracts.StateAndRef;
//import net.corda.core.flows.*;
//import net.corda.core.identity.Party;
//import net.corda.core.transactions.SignedTransaction;
//import net.corda.core.transactions.TransactionBuilder;
//import net.corda.core.utilities.ProgressTracker;
//
//import java.security.PublicKey;
//import java.util.List;
//
///* Our flow, automating the process of updating the ledger.
// * See src/main/java/examples/ArtTransferFlowInitiator.java for an example. */
//
//@InitiatingFlow
//@StartableByRPC
//public class TokenTransferFlow extends FlowLogic<SignedTransaction> {
//    private final Party newOwner;
//    private final int amount;
//    private List<StateAndRef<TokenState>> inputStateAndRefs = getServiceHub().getVaultService().queryBy(TokenState.class).getStates();
//
//    public TokenTransferFlow(Party newOwner, int amount, List<StateAndRef<TokenState>> inputStateAndRefs) {
//        this.newOwner = newOwner;
//        this.amount = amount;
//        this.inputStateAndRefs = inputStateAndRefs;
//    }
//
//    private final ProgressTracker progressTracker = new ProgressTracker();
//
//    @Override
//    public ProgressTracker getProgressTracker() {
//        return progressTracker;
//    }
//
//    @Suspendable
//    @Override
//    public SignedTransaction call() throws FlowException {
//        // We choose our transaction's notary (the notary prevents double-spends).
//        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
//        // We get a reference to our own identity.
//        Party issuer = getOurIdentity();
//
//        /* ============================================================================
//         *         TODO 1 - Create our new TokenState to represent on-ledger tokens!
//         * ===========================================================================*/
//        // We create our new TokenState.
//        TokenState tokenState = new TokenState(issuer, newOwner, amount);
//
//        // Map over list of input token states ==> get sum total value
//        // Then ==> either equal to or greater than output
//        // If = to output single new state is produced
//        // If > than single output two new states are produced
//
//
//        /* ============================================================================
//         *      TODO 3 - Build our token issuance transaction to update the ledger!
//         * ===========================================================================*/
//        // We build our transaction.
//        TransactionBuilder txBuilder = new TransactionBuilder();
//
//        txBuilder.setNotary(notary);
//
//        // Create a check variable for the amount of all inputs to be consumed.
//        int checkInput = 0;
//
//        // Iterate through all retured states from the vault and increase checkInput by the value of each of the inputStates.
//        for (StateAndRef input: inputStateAndRefs) {
//            if (checkInput <= amount) {
//                txBuilder.addInputState(input);
//                // Why doesn't this work?
//                checkInput += (TokenState) input.getState().getData().getAmount();
//            }
//        }
//
//        TokenContract.Commands.Issue commandData = new TokenContract.Commands.Issue();
//        List<PublicKey> requiredSigners = ImmutableList.of(issuer.getOwningKey());
//        txBuilder.addCommand(commandData, requiredSigners);
//
//        /* ============================================================================
//         *          TODO 2 - Write our TokenContract to control token issuance!
//         * ===========================================================================*/
//        // We sign the transaction with our private key, making it immutable.
//        SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(txBuilder);
//
//        // We check our transaction is valid based on its contracts.
//        txBuilder.verify(getServiceHub());
//
//        // We get the transaction notarised and recorded automatically by the platform.
//        return subFlow(new FinalityFlow(signedTransaction));
//    }
//}