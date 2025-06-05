import MockAdapter from "axios-mock-adapter";
import axiosHttp from "./verifyAuth";

describe("verifyAuth interceptor", () => {
  let mock: MockAdapter;

  beforeEach(() => {
    mock = new MockAdapter(axiosHttp);
    localStorage.clear();
  });

  afterEach(() => {
    mock.restore();
  });

  it("should add Authorization and X-User-ID headers if token and userId are present", async () => {
    localStorage.setItem("token", "test-token");
    localStorage.setItem("userId", "user-123");

    mock.onGet("/test").reply((config) => {
      // Axios may use AxiosHeaders or a plain object
      const headers = config.headers || {};

      // For AxiosHeaders, use get method
      const getHeader = (name: string) =>
        typeof headers.get === "function" ? headers.get(name) : headers[name];

      expect(getHeader("Authorization")).toBe("Bearer test-token");
      expect(getHeader("X-User-ID")).toBe("user-123");
      return [200, { success: true }];
    });

    const response = await axiosHttp.get("/test");
    expect(response.status).toBe(200);
    expect(response.data).toEqual({ success: true });
  });

  it("should not add Authorization header if token is not present", async () => {
    localStorage.removeItem("token");
    localStorage.setItem("userId", "user-123");


    mock.onGet("/test").reply((config) => {
      expect(config.headers?.Authorization).toBeUndefined();
      expect(config.headers?.["X-User-ID"]).toBe("user-123");
      return [200, { success: true }];
    });

    const response = await axiosHttp.get("/test");
    expect(response.status).toBe(200);
    expect(response.data).toEqual({ success: true });
  });

  it("should reject with error on 401 response", async () => {
    mock.onGet("/test").reply(401, { message: "Unauthorized" });

    await expect(axiosHttp.get("/test")).rejects.toMatchObject({
      response: expect.objectContaining({ status: 401 }),
    });
  });
});