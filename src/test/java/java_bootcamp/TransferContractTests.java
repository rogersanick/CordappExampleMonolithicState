//package java_bootcamp;
//
//import net.corda.core.contracts.Contract;
//import net.corda.core.identity.CordaX500Name;
//import net.corda.testing.contracts.DummyState;
//import net.corda.testing.core.DummyCommandData;
//import net.corda.testing.core.TestIdentity;
//import net.corda.testing.node.MockServices;
//import org.junit.Test;
//
//import static net.corda.testing.node.NodeTestUtils.transaction;
//
//public class TransferContractTests {
//    private final TestIdentity alice = new TestIdentity(new CordaX500Name("Alice", "", "GB"));
//    private final TestIdentity bob = new TestIdentity(new CordaX500Name("Bob", "", "GB"));
//    private MockServices ledgerServices = new MockServices(new TestIdentity(new CordaX500Name("TestId", "", "GB")));
//    private TokenState oldTokenState = new TokenState(alice.getParty(), bob.getParty(), 1);
//    private TokenState newTokenState = new TokenState(alice.getParty(), alice.getParty(), 1);
//    private TokenState oldFungibleTokenTestState1 = new TokenState(alice.getParty(), bob.getParty(), 1);
//    private TokenState oldFungibleTokenTestState2 = new TokenState(alice.getParty(), bob.getParty(), 3);
//    private TokenState newFungibleTokenState = new TokenState(alice.getParty(), alice.getParty(), 2);
//    private TokenState newFungibleChangeTokenState = new TokenState(alice.getParty(), bob.getParty(), 2);
//
//    @Test
//    public void tokenContractImplementsContract() {
//        assert(new TokenContract() instanceof Contract);
//    }
//
//    @Test
//    public void tokenContractRequiresNonZeroInputsInTheTransaction() {
//        transaction(ledgerServices, tx -> {
//            // Has an input, will pass.
//            tx.input(TokenContract.ID, oldTokenState);
//            tx.output(TokenContract.ID, oldTokenState);
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Transfer());
//            tx.verifies();
//            return null;
//        });
//
//        transaction(ledgerServices, tx -> {
//            // Has no input, will not verify.
//            tx.output(TokenContract.ID, oldTokenState);
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Transfer());
//            tx.fails();
//            return null;
//        });
//    }
//
//    @Test
//    public void tokenContractRequiresOneOrTwoOutputsInTheTransaction() {
//
//        transaction(ledgerServices, tx -> {
//            // has one input with an amount that matches the output
//            tx.input(TokenContract.ID, oldTokenState);
//            // Has one outputs, will verify.
//            tx.output(TokenContract.ID, newTokenState);
//            // TODO ==> ADD APPROPRIATE FIELDS TO TRANSFER COMMAND;
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Transfer());
//            tx.verifies();
//            return null;
//        });
//
//        transaction(ledgerServices, tx -> {
//            // has two inputs with amounts that are greater than the output
//            tx.input(TokenContract.ID, oldFungibleTokenTestState1);
//            tx.input(TokenContract.ID, oldFungibleTokenTestState2);
//            // Has two outputs, will verify.
//            tx.output(TokenContract.ID, newFungibleTokenState);
//            tx.output(TokenContract.ID, newFungibleChangeTokenState);
//            // TODO ==> ADD APPROPRIATE FIELDS TO TRANSFER COMMAND;
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Transfer());
//            tx.verifies();
//            return null;
//        });
//    }
//
//    @Test
//    public void tokenContractRequiresTheTransactionsOutputToBeATokenState() {
//        transaction(ledgerServices, tx -> {
//            // Has wrong output type, will fail.
//            tx.input(TokenContract.ID, oldTokenState);
//            tx.output(TokenContract.ID, new DummyState());
//            // TODO ==> ADD APPROPRIATE FIELDS TO TRANSFER COMMAND;
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Issue());
//            tx.fails();
//            return null;
//        });
//
//        transaction(ledgerServices, tx -> {
//            // Has correct output type, will verify.
//            tx.input(TokenContract.ID, oldTokenState);
//            tx.output(TokenContract.ID, newTokenState);
//            // TODO ==> ADD APPROPRIATE FIELDS TO TRANSFER COMMAND;
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Issue());
//            tx.verifies();
//            return null;
//        });
//    }
//
//    @Test
//    public void tokenContractRequiresTheTransactionsCommandToBeAnIssueCommand() {
//        transaction(ledgerServices, tx -> {
//            // Has wrong command type, will fail.
//            tx.input(TokenContract.ID, oldTokenState);
//            tx.output(TokenContract.ID, newTokenState);
//            tx.command(alice.getPublicKey(), DummyCommandData.INSTANCE);
//            tx.fails();
//            return null;
//        });
//
//        transaction(ledgerServices, tx -> {
//            // Has correct command type, will verify.
//            tx.input(TokenContract.ID, oldTokenState);
//            tx.output(TokenContract.ID, newTokenState);
//            tx.command(alice.getPublicKey(), new TokenContract.Commands.Transfer());
//            tx.verifies();
//            return null;
//        });
//    }
//
//}
