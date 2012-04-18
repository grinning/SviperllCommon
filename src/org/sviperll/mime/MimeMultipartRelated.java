package org.sviperll.mime;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

/**
 *
 * @author vir
 */
public class MimeMultipartRelated extends MimeMultipart {
    private final ContentType baseContentTypeObject;
    private String rootId = null;

    public MimeMultipartRelated() {
        super("related");
        try {
            baseContentTypeObject = new ContentType(contentType);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addBodyPart(BodyPart part) throws MessagingException {
        super.addBodyPart(part);
        updateContentType();
    }

    @Override
    public void addBodyPart(BodyPart part, int index) throws MessagingException {
        super.addBodyPart(part, index);
        updateContentType();
    }

    @Override
    public boolean removeBodyPart(BodyPart part) throws MessagingException {
        boolean res = super.removeBodyPart(part);
        updateContentType(true);
        return res;
    }

    @Override
    public void removeBodyPart(int index) throws MessagingException {
        super.removeBodyPart(index);
        updateContentType(true);
    }

    public void setRoot(String contentId) throws MessagingException {
        rootId = contentId;
        updateContentType();
    }

    public void useDefaultRoot() throws MessagingException {
        rootId = null;
        updateContentType();
    }

    private void updateContentType(boolean cleanDefaultRoot) throws MessagingException {
        BodyPart part = null;
        if (rootId == null) {
            part = getBodyPart(0);
        } else {
            part = getBodyPart(rootId);
            if (part == null) {
                if (cleanDefaultRoot)
                    rootId = null;
                else
                    throw new MessagingException("Can not set root: " + rootId + ": not found");
            }
        }
        if (part != null) {
            String primaryType = baseContentTypeObject.getPrimaryType();
            String subType = baseContentTypeObject.getSubType();
            ParameterList params = baseContentTypeObject.getParameterList();
            ContentType newContentType = new ContentType(primaryType, subType, params);
            ContentType rootContentType = new ContentType(part.getContentType());
            newContentType.setParameter("type", rootContentType.getBaseType());
            if (rootId != null)
                newContentType.setParameter("start", rootId);
            contentType = newContentType.toString();
        }
    }

    private void updateContentType() throws MessagingException {
        updateContentType(false);
    }
}
