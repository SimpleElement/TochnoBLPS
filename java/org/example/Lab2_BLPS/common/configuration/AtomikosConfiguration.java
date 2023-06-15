package org.example.Lab2_BLPS.common.configuration;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

@Configuration
@EnableTransactionManagement
public class AtomikosConfiguration {
    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager transactionManager = new UserTransactionManager();
        transactionManager.setForceShutdown(false);
        return transactionManager;
    }

    @Bean
    public UserTransaction atomikosUserTransaction() throws Exception {
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(300);
        return userTransaction;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws Exception {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionManager(atomikosTransactionManager());
        transactionManager.setUserTransaction(atomikosUserTransaction());
        return transactionManager;
    }
}
