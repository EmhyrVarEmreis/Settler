package pl.morecraft.dev.settler.service.dictionary.implementation;

import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.domain.dictionaries.SettlementType;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProvider;
import pl.morecraft.dev.settler.service.dictionary.DictionaryProviderFor;

import java.util.Arrays;
import java.util.List;

@Service
@DictionaryProviderFor("settlementType")
public class SettlementTypeDictionaryProvider implements DictionaryProvider {
    @Override
    public List load() {
        return Arrays.asList(SettlementType.values());
    }
}
