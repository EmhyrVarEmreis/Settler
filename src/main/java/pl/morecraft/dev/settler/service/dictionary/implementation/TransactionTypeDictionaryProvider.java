package pl.morecraft.dev.settler.service.dictionary.implementation;

import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProvider;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProviderFor;

import java.util.Arrays;
import java.util.List;

@Service
@DictionaryProviderFor("transactionType")
public class TransactionTypeDictionaryProvider implements DictionaryProvider {
    @Override
    public List loadProvider() {
        return Arrays.asList(TransactionType.values());
    }
}
