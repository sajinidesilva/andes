/*
*  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.andes.kernel;

import java.util.List;

/**
 * This interface works as an abstraction for the actual implementation of how a message is
 * persisted to the database. Message may be stored through disruptor or without it. And in some
 * cases messages that need not be persisted will be stored in memory. Implementations of this
 * interface should handle that logic.
 */
public interface MessageStoreManager {

    /**
     * Initialisation of MessageStoreManager
     *
     * @param durableMessageStore
     *         MessageStore implementation to be used as the durable message store.
     * @throws AndesException
     */
    public void initialise(MessageStore durableMessageStore) throws AndesException;

    /**
     * Store Metadata of the message
     *
     * @param metadata
     *         AndesMessageMetadata
     * @param channelID
     *         channel ID
     * @throws AndesException
     */
    public void storeMetadata(AndesMessageMetadata metadata, long channelID) throws AndesException;

    /**
     * Store Message Content as parts (chunks)
     *
     * @param messagePart
     *         AndesMessagePart
     * @throws AndesException
     */
    public void storeMessageContent(AndesMessagePart messagePart) throws AndesException;

    /**
     * Handle ack received event
     *
     * @param ackData
     *         AndesAckData
     * @throws AndesException
     */
    public void ackReceived(AndesAckData ackData) throws AndesException;

    /**
     * remove message content chunks of messages
     *
     * @param messageIdList
     *         list of message ids whose content should be removed
     * @throws AndesException
     */
    public void deleteMessageParts(List<Long> messageIdList) throws AndesException;


    /**
     * process the ack received messages
     *
     * @param ackList
     *         ack message list to process
     * @throws AndesException
     */
    public void processAckReceived(List<AndesAckData> ackList) throws AndesException;

    /**
     * decrement message count of queue
     *
     * @param queueName
     *         name of queue
     * @throws AndesException
     */
    public void decrementQueueCount(String queueName) throws AndesException;


    /**
     * increment message count of queue
     *
     * @param queueName
     *         name of queue
     * @throws AndesException
     */
    public void incrementQueueCount(String queueName) throws AndesException;


    /**
     * store a message content part
     *
     * @param messageParts
     *         message parts to store
     * @throws AndesException
     */
    public void storeMessagePart(List<AndesMessagePart> messageParts) throws AndesException;


    /**
     * store metadata for message
     *
     * @param messageMetadata
     *         metadata list to store
     * @throws AndesException
     */
    public void storeMetaData(List<AndesMessageMetadata> messageMetadata) throws AndesException;

    /**
     * get message content from store
     *
     * @param messageId
     *         id of the message whose content chunk should be received
     * @param offsetValue
     *         offset of the message chunk
     * @return message content part
     * @throws AndesException
     */
    public AndesMessagePart getContent(long messageId, int offsetValue) throws AndesException;

    /**
     * delete messages and optionally send to DLC
     *
     * @param messagesToRemove
     *         messages to remove
     * @param moveToDeadLetterChannel
     *         whether to send to DLC
     * @throws AndesException
     */
    public void deleteMessages(List<AndesRemovableMetadata> messagesToRemove,
                               boolean moveToDeadLetterChannel) throws AndesException;

    /**
     * get expired messages from store
     *
     * @param limit
     *         expiration limit
     * @return list of messages to remove
     * @throws AndesException
     */
    public List<AndesRemovableMetadata> getExpiredMessages(int limit) throws AndesException;

    /**
     * get message metadata list for a queue giving a range of message ids
     *
     * @param queueName
     *         name of the queue
     * @param firstMsgId
     *         first message id to seek from
     * @param lastMsgID
     *         last message id
     * @return list of metadata
     * @throws AndesException
     */
    public List<AndesMessageMetadata> getMetaDataList(final String queueName, long firstMsgId,
                                                      long lastMsgID) throws AndesException;

    /**
     * @param queueName
     *         get message metadata list for a queue giving a start message id and a number of
     *         messages to receive
     * @param firstMsgId
     *         first message id to seek from
     * @param count
     *         max message count to return
     * @return list of metadata
     * @throws AndesException
     */
    public List<AndesMessageMetadata> getNextNMessageMetadataFromQueue(final String queueName,
                                                                       long firstMsgId, int count)
            throws AndesException;

    /**
     * close the store manager
     */
    public void close();


}
