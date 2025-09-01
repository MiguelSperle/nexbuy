package com.miguelsperle.nexbuy.module.freight.infrastructure.adapters.in.consumers;

import com.miguelsperle.nexbuy.module.freight.application.ports.out.persistence.FreightRepository;
import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import com.miguelsperle.nexbuy.shared.application.commands.CreateFreightCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFreightConsumer {
    private final FreightRepository freightRepository;

    private static final String CREATE_FREIGHT_QUEUE = "create.freight.queue";

    @RabbitListener(queues = CREATE_FREIGHT_QUEUE)
    public void onMessage(CreateFreightCommand createFreightCommand) {
        final Freight freight = Freight.newFreight(
                createFreightCommand.orderId(),
                createFreightCommand.name(),
                createFreightCommand.companyName(),
                createFreightCommand.price(),
                createFreightCommand.estimatedTime(),
                createFreightCommand.minTime(),
                createFreightCommand.maxTime()
        );

        this.saveFreight(freight);
    }

    private void saveFreight(Freight freight) {
        this.freightRepository.save(freight);
    }
}
