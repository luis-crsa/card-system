CREATE OR REPLACE FUNCTION encrypt_cartao_password()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT' OR NEW.senha IS DISTINCT FROM OLD.senha) THEN
        IF NEW.senha IS NOT NULL THEN
            NEW.senha := crypt(NEW.senha, gen_salt('bf'));
END IF;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_encrypt_cartao_password
    BEFORE INSERT OR UPDATE ON cartoes
                         FOR EACH ROW
                         EXECUTE FUNCTION encrypt_cartao_password();