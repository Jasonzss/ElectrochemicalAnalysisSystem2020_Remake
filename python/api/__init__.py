from api_exception import APIException


def error_handler(app):
    @app.errorhandler(Exception)
    def handle_error(e):
        if isinstance(e, APIException):
            return e
